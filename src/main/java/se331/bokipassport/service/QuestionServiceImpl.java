package se331.bokipassport.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se331.bokipassport.dao.QuestionDao;
import se331.bokipassport.entity.Question;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    final QuestionDao questionDao;

    @Override
    public List<Question> findByExamId(Long examId) {
        return questionDao.findByExamId(examId);
    }
}
