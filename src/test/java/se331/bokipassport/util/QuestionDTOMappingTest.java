package se331.bokipassport.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import se331.bokipassport.entity.Answer;
import se331.bokipassport.entity.Question;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestionDTOMappingTest {

    private static Question question() {
        Question question = Question.builder().id(1L).questionText("What is BokiAI?").build();
        Answer answer = Answer.builder()
                .id(10L)
                .answerText("The Agentic Artificial Inteligence")
                .isCorrect(true)
                .question(question)
                .build();
        question.setAnswerList(new ArrayList<>(List.of(answer)));
        return question;
    }

    @Test
    void theShapeCarriesTheQuestionAndItsChoices() {
        var dtos = LabMapper.INSTANCE.getQuestionDto(List.of(question()));

        assertEquals(1L, dtos.get(0).getId());
        assertEquals(1, dtos.get(0).getAnswerChoices().size());
        assertEquals(10L, dtos.get(0).getAnswerChoices().get(0).getId());
    }

    @Test
    void theAdminViewCarriesTheAnswerKey() throws Exception {
        String json = new ObjectMapper().writeValueAsString(
                LabMapper.INSTANCE.getQuestionDto(List.of(question())));

        assertTrue(json.contains("\"isCorrect\":true"), "the admin view must carry the answer key");
    }

    @Test
    void theCandidateViewNeverCarriesTheAnswerKey() throws Exception {
        String json = new ObjectMapper().writeValueAsString(
                LabMapper.INSTANCE.getCandidateQuestionDto(List.of(question())));

        assertFalse(json.contains("isCorrect"), "the candidate view must not leak the answer key");
    }

    @Test
    void theCandidateViewStillCarriesTheChoices() {
        var dtos = LabMapper.INSTANCE.getCandidateQuestionDto(List.of(question()));

        assertEquals(1, dtos.get(0).getAnswerChoices().size());
        assertEquals("The Agentic Artificial Inteligence", dtos.get(0).getAnswerChoices().get(0).getAnswerText());
    }

    @Test
    void noShapeCarriesAForeignKeyForItsParent() throws Exception {
        String candidate = new ObjectMapper().writeValueAsString(
                LabMapper.INSTANCE.getCandidateQuestionDto(List.of(question())));
        String admin = new ObjectMapper().writeValueAsString(
                LabMapper.INSTANCE.getQuestionDto(List.of(question())));

        assertFalse(candidate.contains("questionId"), "the parent is implied by the nesting");
        assertFalse(candidate.contains("examId"), "the parent is implied by the nesting");
        assertFalse(admin.contains("questionId"), "the parent is implied by the nesting");
        assertFalse(admin.contains("examId"), "the parent is implied by the nesting");
    }
}
