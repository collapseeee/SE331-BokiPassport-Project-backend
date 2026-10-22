package se331.bokipassport.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import se331.bokipassport.dao.ExamDao;
import se331.bokipassport.dao.LevelDao;
import se331.bokipassport.dao.QuestionDao;
import se331.bokipassport.dao.UserDao;
import se331.bokipassport.dao.UserExamHistoryDao;
import se331.bokipassport.entity.Answer;
import se331.bokipassport.entity.Exam;
import se331.bokipassport.entity.Level;
import se331.bokipassport.entity.Question;
import se331.bokipassport.entity.User;
import se331.bokipassport.entity.UserExamHistory;
import se331.bokipassport.entity.dto.ExamSubmitRequest;
import se331.bokipassport.entity.dto.ExamSubmitResponse;
import se331.bokipassport.security.user.CurrentUserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExamAttemptServiceImplTest {

    ExamDao examDao;
    QuestionDao questionDao;
    LevelDao levelDao;
    UserDao userDao;
    UserExamHistoryDao userExamHistoryDao;
    CurrentUserService currentUserService;
    ExamAttemptServiceImpl service;

    Level level1;
    Level level2;
    Exam exam;
    Question question;
    User user;

    @BeforeEach
    void setUp() {
        examDao = mock(ExamDao.class);
        questionDao = mock(QuestionDao.class);
        levelDao = mock(LevelDao.class);
        userDao = mock(UserDao.class);
        userExamHistoryDao = mock(UserExamHistoryDao.class);
        currentUserService = mock(CurrentUserService.class);
        service = new ExamAttemptServiceImpl(examDao, questionDao, levelDao, userDao, userExamHistoryDao,
                currentUserService);

        level1 = Level.builder().id(1L).name("Level 1").passCriteria(0.5).build();
        level2 = Level.builder().id(2L).name("Level 2").passCriteria(0.55).build();

        exam = Exam.builder().id(1L).name("Upgrade to Level 2 examination").level(level1).build();

        question = Question.builder().id(1L).questionText("What is BokiAI?").exam(exam).build();
        Answer correct = Answer.builder().id(10L).answerText("correct").isCorrect(true).question(question).build();
        Answer wrong = Answer.builder().id(11L).answerText("wrong").isCorrect(false).question(question).build();
        question.setAnswerList(new ArrayList<>(List.of(correct, wrong)));

        user = User.builder().id(2L).levels(new ArrayList<>(List.of(level1))).build();
    }

    private ExamSubmitRequest requestWith(Long questionId, Long answerId) {
        Map<Long, Long> answers = new HashMap<>();
        answers.put(questionId, answerId);
        return ExamSubmitRequest.builder().answers(answers).build();
    }

    private void givenExamAndUser(long userLevelId) {
        when(examDao.findById(1L)).thenReturn(exam);
        when(questionDao.findByExamId(1L)).thenReturn(List.of(question));
        when(currentUserService.getCurrentLevelId()).thenReturn(userLevelId);
        when(currentUserService.isAdmin()).thenReturn(false);
        when(currentUserService.getCurrentProfile()).thenReturn(user);
    }

    @Test
    void aUserBelowTheExamLevelCannotSubmit() {
        givenExamAndUser(0L);

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class,
                () -> service.submit(1L, requestWith(1L, 10L)));

        assertEquals(HttpStatus.FORBIDDEN, thrown.getStatusCode());
        verify(userExamHistoryDao, never()).save(any());
    }

    @Test
    void anUnknownExamIsNotFound() {
        when(examDao.findById(99L)).thenReturn(null);

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class,
                () -> service.submit(99L, requestWith(1L, 10L)));

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
    }

    @Test
    void passingWritesTheHistoryAndAwardsTheNextLevel() {
        givenExamAndUser(1L);
        when(levelDao.findNextAfter(1L)).thenReturn(level2);

        ExamSubmitResponse response = service.submit(1L, requestWith(1L, 10L));

        assertEquals(1, response.getTotalScore());
        assertEquals(1, response.getMaxScore());
        assertTrue(response.getIsPassed());
        assertEquals(2L, response.getAwardedLevelId());
        assertTrue(user.getLevels().contains(level2));
        verify(userExamHistoryDao).save(any(UserExamHistory.class));
        verify(userDao).saveProfile(user);
    }

    @Test
    void submittingAgainDoesNotAwardTheSameLevelTwice() {
        givenExamAndUser(1L);
        user.getLevels().add(level2);
        when(levelDao.findNextAfter(1L)).thenReturn(level2);

        ExamSubmitResponse response = service.submit(1L, requestWith(1L, 10L));

        assertTrue(response.getIsPassed());
        assertNull(response.getAwardedLevelId());
        verify(userDao, never()).saveProfile(any());
    }

    @Test
    void failingAwardsNoLevel() {
        givenExamAndUser(1L);
        when(levelDao.findNextAfter(1L)).thenReturn(level2);

        ExamSubmitResponse response = service.submit(1L, requestWith(1L, 11L));

        assertEquals(0, response.getTotalScore());
        assertFalse(response.getIsPassed());
        assertNull(response.getAwardedLevelId());
        verify(userExamHistoryDao).save(any(UserExamHistory.class));
        verify(userDao, never()).saveProfile(any());
    }
}
