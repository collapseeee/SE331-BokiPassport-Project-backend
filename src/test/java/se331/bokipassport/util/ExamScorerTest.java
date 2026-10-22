package se331.bokipassport.util;

import org.junit.jupiter.api.Test;
import se331.bokipassport.entity.Answer;
import se331.bokipassport.entity.Question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExamScorerTest {

    private static Question question(long id, long correctAnswerId) {
        Question question = Question.builder().id(id).questionText("Question " + id).build();
        Answer correct = Answer.builder().id(correctAnswerId).answerText("correct").isCorrect(true).question(question).build();
        Answer wrong = Answer.builder().id(correctAnswerId + 100).answerText("wrong").isCorrect(false).question(question).build();
        question.setAnswerList(List.of(correct, wrong));
        return question;
    }

    @Test
    void countsAnAnswerOnlyWhenTheIdBelongsToThatQuestion() {
        List<Question> questions = List.of(question(1, 10), question(2, 20));
        Map<Long, Long> answers = new HashMap<>(Map.of(1L, 10L, 2L, 20L));

        assertEquals(2, ExamScorer.countCorrect(questions, answers));
    }

    @Test
    void anUnansweredQuestionCountsAsWrongNotAsAnError() {
        List<Question> questions = List.of(question(1, 10), question(2, 20));
        Map<Long, Long> answers = new HashMap<>(Map.of(1L, 10L));

        assertEquals(1, ExamScorer.countCorrect(questions, answers));
    }

    @Test
    void ignoresAnAnswerIdThatBelongsToADifferentQuestion() {
        List<Question> questions = List.of(question(1, 10));
        Map<Long, Long> answers = new HashMap<>(Map.of(1L, 110L));

        assertEquals(0, ExamScorer.countCorrect(questions, answers));
    }

    @Test
    void anEmptyAnswerMapScoresZero() {
        assertEquals(0, ExamScorer.countCorrect(List.of(question(1, 10)), new HashMap<>()));
        assertEquals(0, ExamScorer.countCorrect(List.of(question(1, 10)), null));
    }

    @Test
    void thePassThresholdMatchesTheFrontendFormula() {
        // Math.round(0.5 * 5) == 3
        assertTrue(ExamScorer.isPassed(3, 5, 0.5));
        assertFalse(ExamScorer.isPassed(2, 5, 0.5));
    }

    @Test
    void anExamWithNoPassCriteriaIsNotPassed() {
        assertFalse(ExamScorer.isPassed(3, 5, null));
        assertFalse(ExamScorer.isPassed(0, 0, 0.5));
    }

    @Test
    void aQuestionWithNoAnswerListScoresZeroInsteadOfThrowing() {
        Question empty = Question.builder().id(1L).questionText("Question 1").build();

        assertEquals(0, ExamScorer.countCorrect(List.of(empty), new HashMap<>(Map.of(1L, 10L))));
    }
}
