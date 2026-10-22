package se331.bokipassport.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se331.bokipassport.dao.ExamDao;
import se331.bokipassport.dao.LevelDao;
import se331.bokipassport.dao.QuestionDao;
import se331.bokipassport.dao.UserDao;
import se331.bokipassport.dao.UserExamHistoryDao;
import se331.bokipassport.entity.Exam;
import se331.bokipassport.entity.Level;
import se331.bokipassport.entity.Question;
import se331.bokipassport.entity.User;
import se331.bokipassport.entity.UserExamHistory;
import se331.bokipassport.entity.dto.ExamSubmitRequest;
import se331.bokipassport.entity.dto.ExamSubmitResponse;
import se331.bokipassport.security.user.UserAccount;
import se331.bokipassport.util.ExamAccess;
import se331.bokipassport.util.ExamScorer;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamAttemptServiceImpl implements ExamAttemptService {
    private final ExamDao examDao;
    private final QuestionDao questionDao;
    private final LevelDao levelDao;
    private final UserDao userDao;
    private final UserExamHistoryDao userExamHistoryDao;

    @Override
    public ExamSubmitResponse submit(Long examId, ExamSubmitRequest request, UserAccount account) {
        Exam exam = examDao.findById(examId);
        if (exam == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Exam not found");
        }
        User user = callerProfile(account);
        Level examLevel = exam.getLevel();
        Long examLevelId = examLevel == null ? null : examLevel.getId();
        boolean allowed = ExamAccess.canAccessExam(
                examLevelId, ExamAccess.highestLevelId(user == null ? null : user.getLevels()),
                ExamAccess.isAdmin(currentAuthentication()));
        if (!allowed) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Your level is too low to access this exam");
        }

        List<Question> questions = questionDao.findByExamId(examId);
        int totalScore = ExamScorer.countCorrect(questions, request == null ? null : request.getAnswers());
        int maxScore = questions.size();
        boolean isPassed = ExamScorer.isPassed(totalScore, maxScore,
                examLevel == null ? null : examLevel.getPassCriteria());

        userExamHistoryDao.save(UserExamHistory.builder()
                .totalScore(totalScore)
                .isPassed(isPassed)
                .exam(exam)
                .user(user)
                .build());

        Long awardedLevelId = null;
        List<Level> userLevels = user.getLevels();
        if (isPassed && examLevelId != null && userLevels != null) {
            Level nextLevel = levelDao.findNextAfter(examLevelId);
            boolean alreadyHasLevel = nextLevel != null && userLevels.stream()
                    .anyMatch(level -> level.getId().equals(nextLevel.getId()));
            if (nextLevel != null && !alreadyHasLevel) {
                userLevels.add(nextLevel);
                userDao.saveProfile(user);
                awardedLevelId = nextLevel.getId();
            }
        }

        return ExamSubmitResponse.builder()
                .examId(examId)
                .totalScore(totalScore)
                .maxScore(maxScore)
                .isPassed(isPassed)
                .awardedLevelId(awardedLevelId)
                .build();
    }

    /**
     * The account in the security context is detached, so its collections cannot be read.
     * The profile is loaded again to get the caller's levels.
     */
    private User callerProfile(UserAccount account) {
        if (account == null || account.getProfile() == null) {
            return null;
        }
        return userDao.findProfileById(account.getProfile().getId());
    }

    private Authentication currentAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
