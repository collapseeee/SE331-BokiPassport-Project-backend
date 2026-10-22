package se331.bokipassport.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
import se331.bokipassport.security.user.CurrentUserService;
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
    private final CurrentUserService currentUserService;

    @Override
    public ExamSubmitResponse submit(Long examId, ExamSubmitRequest request) {
        Exam exam = examDao.findById(examId);
        if (exam == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Exam not found");
        }
        Level examLevel = exam.getLevel();
        Long examLevelId = examLevel == null ? null : examLevel.getId();
        boolean allowed = ExamAccess.canAccessExam(
                examLevelId, currentUserService.getCurrentLevelId(), currentUserService.isAdmin());
        if (!allowed) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Your level is too low to access this exam");
        }

        List<Question> questions = questionDao.findByExamId(examId);
        int totalScore = ExamScorer.countCorrect(questions, request == null ? null : request.getAnswers());
        int maxScore = questions.size();
        boolean isPassed = ExamScorer.isPassed(totalScore, maxScore,
                examLevel == null ? null : examLevel.getPassCriteria());

        UserAccount account = currentUserService.getCurrentUser();
        User user = account.getProfile();
        userExamHistoryDao.save(UserExamHistory.builder()
                .totalScore(totalScore)
                .isPassed(isPassed)
                .exam(exam)
                .user(user)
                .build());

        Long awardedLevelId = null;
        if (isPassed && examLevelId != null) {
            Level nextLevel = levelDao.findById(examLevelId + 1);
            if (nextLevel != null && !user.getLevels().contains(nextLevel)) {
                user.getLevels().add(nextLevel);
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
}
