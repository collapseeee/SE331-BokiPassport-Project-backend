package se331.bokipassport.service;

import se331.bokipassport.entity.Question;
import se331.bokipassport.entity.dto.QuestionDTO;
import se331.bokipassport.security.user.UserAccount;

import java.util.List;

public interface QuestionService {
    List<Question> findByExamId(Long examId, UserAccount account);
    Question updateQuestion(Long id, QuestionDTO questionDTO);
    List<Question> saveAllQuestions(Long examId, List<QuestionDTO> questionDTO);
    Question createQuestion(Long examId, QuestionDTO questionDTO);
    void deleteQuestion(Long id);
}
