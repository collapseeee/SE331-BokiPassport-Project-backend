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
    public List<Question> findByExamId(Long examId) {
        return questionRepository.findByExam_Id(examId);
    }
}
