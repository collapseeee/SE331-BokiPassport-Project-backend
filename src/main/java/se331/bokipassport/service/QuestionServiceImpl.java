package se331.bokipassport.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se331.bokipassport.dao.ExamDao;
import se331.bokipassport.dao.QuestionDao;
import se331.bokipassport.entity.Exam;
import se331.bokipassport.entity.Level;
import se331.bokipassport.entity.Question;
import se331.bokipassport.security.user.CurrentUserService;
import se331.bokipassport.util.ExamAccess;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    final QuestionDao questionDao;
    final ExamDao examDao;
    final CurrentUserService currentUserService;

    @Override
    public List<Question> findByExamId(Long examId) {
        Exam exam = examDao.findById(examId);
        if (exam == null) {
            return List.of();
        }
        Level examLevel = exam.getLevel();
        Long examLevelId = examLevel == null ? null : examLevel.getId();
        boolean allowed = ExamAccess.canAccessExam(
                examLevelId, currentUserService.getCurrentLevelId(), currentUserService.isAdmin());
        if (!allowed) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Your level is too low to access this exam");
        }
        return questionDao.findByExamId(examId);
    }
}
