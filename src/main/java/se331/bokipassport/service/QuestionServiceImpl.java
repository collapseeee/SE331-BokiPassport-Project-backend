package se331.bokipassport.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se331.bokipassport.dao.ExamDao;
import se331.bokipassport.dao.QuestionDao;
import se331.bokipassport.dao.UserDao;
import se331.bokipassport.entity.Exam;
import se331.bokipassport.entity.Level;
import se331.bokipassport.entity.Question;
import se331.bokipassport.entity.User;
import se331.bokipassport.security.user.UserAccount;
import se331.bokipassport.util.ExamAccess;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    final QuestionDao questionDao;
    final ExamDao examDao;
    final UserDao userDao;

    @Override
    public List<Question> findByExamId(Long examId, UserAccount account) {
        Exam exam = examDao.findById(examId);
        if (exam == null) {
            return List.of();
        }
        Level examLevel = exam.getLevel();
        Long examLevelId = examLevel == null ? null : examLevel.getId();
        boolean allowed = ExamAccess.canAccessExam(
                examLevelId, callerLevelId(account), ExamAccess.isAdmin(currentAuthentication()));
        if (!allowed) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Your level is too low to access this exam");
        }
        return questionDao.findByExamId(examId);
    }

    /**
     * The account in the security context is detached, so its collections cannot be read.
     * The profile is loaded again to get the caller's levels.
     */
    private Long callerLevelId(UserAccount account) {
        User profile = callerProfile(account);
        return profile == null ? 0L : ExamAccess.highestLevelId(profile.getLevels());
    }

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
