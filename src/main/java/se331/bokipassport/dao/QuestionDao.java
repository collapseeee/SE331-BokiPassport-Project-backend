package se331.bokipassport.dao;

import se331.bokipassport.entity.Question;

import java.util.List;

public interface QuestionDao {
    Question findQuestionById(Long id);
    List<Question> findByExamId(Long examId);
    Question saveQuestion(Question question);
    void deleteQuestion(Question question);
}
