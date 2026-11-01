package se331.bokipassport.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se331.bokipassport.dao.ExamDao;
import se331.bokipassport.dao.QuestionDao;
import se331.bokipassport.dao.UserDao;
import se331.bokipassport.entity.*;
import se331.bokipassport.entity.dto.AnswerDTO;
import se331.bokipassport.entity.dto.QuestionDTO;
import se331.bokipassport.security.user.UserAccount;
import se331.bokipassport.util.ExamAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public Question save(Long id, QuestionDTO questionDTO) {
        Question question = questionDao.findQuestionById(id);
        if (question == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question with id " + id + " not found");
        }

        if (Boolean.FALSE.equals(isEmpty(questionDTO.getQuestionText()))) {
            question.setQuestionText(questionDTO.getQuestionText());
        }
        if (questionDTO.getAnswerChoices() != null && !questionDTO.getAnswerChoices().isEmpty()) {
            Map<Long, Answer> existingAnswers = question.getAnswerList().stream()
                    // Map incoming answerId with existed in db. So that it can be updated correctly.
                    .filter(a -> a.getId() != null)
                    .collect(Collectors.toMap(Answer::getId, a -> a));
            for (AnswerDTO answerDTO : questionDTO.getAnswerChoices()) {
                if (answerDTO.getId() != null && existingAnswers.containsKey(answerDTO.getId())) {
                    Answer existing = existingAnswers.get(answerDTO.getId());
                    if (answerDTO.getAnswerText() != null) existing.setAnswerText(answerDTO.getAnswerText());
                    if (answerDTO.getIsCorrect() != null) existing.setIsCorrect(answerDTO.getIsCorrect());
                } else {
                    Answer newAnswer = Answer.builder()
                            .answerText(answerDTO.getAnswerText())
                            .isCorrect(answerDTO.getIsCorrect())
                            .question(question)
                            .build();
                    question.getAnswerList().add(newAnswer);
                }
            }
        }
        return questionDao.save(question);
    }

    @Override
    @Transactional
    public Question create(Long examId, QuestionDTO questionDTO) {
        Exam exam = examDao.findById(examId);
        if (exam == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Exam with id " + examId + " not found");
        }

        Question question = Question.builder()
                .questionText(questionDTO.getQuestionText())
                .exam(exam)
                .answerList(new  ArrayList<>())
                .build();

        if (questionDTO.getAnswerChoices() != null && !questionDTO.getAnswerChoices().isEmpty()) {
            for (AnswerDTO answerDTO : questionDTO.getAnswerChoices()) {
                Answer answer = Answer.builder()
                        .answerText(answerDTO.getAnswerText())
                        .isCorrect(answerDTO.getIsCorrect())
                        .question(question)
                        .build();
                question.getAnswerList().add(answer);
            }
        }
        return questionDao.save(question);
    }

    @Override
    @Transactional
    public List<Question> saveAll(Long examId, List<QuestionDTO> inputQuestionDTO) {
        List<Question> savedQuestions = new ArrayList<>();
        for (QuestionDTO questionDTO : inputQuestionDTO) {
            if (questionDTO.getId() != null) {
                savedQuestions.add(save(questionDTO.getId(), questionDTO));
            } else {
                savedQuestions.add(create(examId, questionDTO));
            }
        }
        return savedQuestions;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Question question = questionDao.findQuestionById(id);
        if (question == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question with id " + id + " not found");
        }
        questionDao.delete(question);
    }

    @Override
    @Transactional
    public void deleteByIds(List<Long> ids) {
        for (Long id : ids) {
            this.delete(id);
        }
    }

    /**
     * The account in the security context is detached, so its collections cannot be read.
     * The profile is loaded again to get the caller's levels.
     */
    public Boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

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
