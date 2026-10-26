package se331.bokipassport.dao;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import se331.bokipassport.entity.Question;
import se331.bokipassport.repository.QuestionRepository;

import java.util.List;

@Repository
@AllArgsConstructor
public class QuestionDaoImpl implements QuestionDao {
    final QuestionRepository questionRepository;

    @Override
    public Question findQuestionById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Question> findByExamId(Long examId) {
        return questionRepository.findByExam_Id(examId);
    }

    @Override
    public Question save(Question question) { return questionRepository.save(question); }

    @Override
    public void delete(Question question) { questionRepository.delete(question); }
}
