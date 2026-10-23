package se331.bokipassport.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import se331.bokipassport.entity.Exam;
import se331.bokipassport.entity.Level;
import se331.bokipassport.entity.UserExamHistory;
import se331.bokipassport.entity.dto.UserExamHistoryDTO;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserExamHistoryDTOMappingTest {

    private static UserExamHistory history() {
        Exam exam = Exam.builder()
                .id(3L)
                .name("Upgrade to Level 4 examination")
                .description("3 Advanced BokiAI design.")
                .build();
        exam.setLevel(Level.builder().id(3L).name("Level 3").passCriteria(0.75).build());
        return UserExamHistory.builder()
                .id(9L)
                .createdAt(Instant.parse("2026-10-22T00:00:00Z"))
                .totalScore(4)
                .isPassed(true)
                .exam(exam)
                .build();
    }

    @Test
    void mapsTheScoreAndNestsTheExam() {
        UserExamHistoryDTO dto = LabMapper.INSTANCE.getUserOwnExamHistoryDto(history());

        assertEquals(4, dto.getTotalScore());
        assertEquals(true, dto.getIsPassed());
        assertEquals(3L, dto.getExam().getId());
        assertEquals("Upgrade to Level 4 examination", dto.getExam().getName());
        assertEquals(3L, dto.getExam().getLevel().getId());
    }

    @Test
    void mapsTheSameShapeInThePageMapping() {
        List<UserExamHistoryDTO> dtos = LabMapper.INSTANCE.getUserExamHistoryDto(
                new PageImpl<>(List.of(history())));

        assertEquals(1, dtos.size());
        assertEquals("Upgrade to Level 4 examination", dtos.get(0).getExam().getName());
    }

    @Test
    void theHistoryCarriesNoForeignKeyForTheExam() {
        List<String> fieldNames = Arrays.stream(UserExamHistoryDTO.class.getDeclaredFields())
                .map(Field::getName)
                .toList();

        assertFalse(fieldNames.contains("examId"), "the exam is nested, not referenced by id");
        assertTrue(fieldNames.contains("exam"));
    }
}
