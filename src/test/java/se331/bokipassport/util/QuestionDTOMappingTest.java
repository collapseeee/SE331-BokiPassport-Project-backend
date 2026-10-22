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
        Question question = Question.builder()
                .id(1L)
                .questionText("What is BokiAI?")
                .build();
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
    void theCandidateShapeCarriesTheQuestionId() {
        var dto = LabMapper.INSTANCE.getQuestionDto(question());

        assertEquals(1L, dto.getId());
        assertEquals(1, dto.getAnswerChoices().size());
        assertEquals(1L, dto.getAnswerChoices().get(0).getQuestionId());
    }

    @Test
    void theAdminShapeCarriesBothTheQuestionIdAndTheKey() {
        var dto = LabMapper.INSTANCE.getQuestionWithKeyDto(question());

        assertEquals(1, dto.getAnswerChoices().size());
        assertEquals(1L, dto.getAnswerChoices().get(0).getQuestionId());
        assertEquals(true, dto.getAnswerChoices().get(0).getIsCorrect());
    }

    @Test
    void theCandidateResponseJsonNeverContainsTheAnswerKey() throws Exception {
        String json = new ObjectMapper().writeValueAsString(LabMapper.INSTANCE.getQuestionDto(question()));

        assertFalse(json.contains("isCorrect"), "the candidate endpoint must not leak the answer key");
    }

    @Test
    void theAdminResponseJsonDoesContainTheAnswerKey() throws Exception {
        String json = new ObjectMapper().writeValueAsString(
                LabMapper.INSTANCE.getQuestionWithKeyDto(question()));

        assertTrue(json.contains("\"isCorrect\":true"), "the admin endpoint must carry the answer key");
    }
}
