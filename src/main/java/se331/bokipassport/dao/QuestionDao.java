package se331.bokipassport.dao;

import se331.bokipassport.entity.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findByExamId(Long examId);
}
