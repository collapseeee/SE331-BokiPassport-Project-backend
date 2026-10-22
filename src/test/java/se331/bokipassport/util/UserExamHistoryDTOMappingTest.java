package se331.bokipassport.util;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import se331.bokipassport.entity.Exam;
import se331.bokipassport.entity.Level;
import se331.bokipassport.entity.UserExamHistory;
import se331.bokipassport.entity.dto.UserExamHistoryDTO;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserExamHistoryDTOMappingTest {

    private static UserExamHistory history() {
        Exam exam = Exam.builder()
                .id(3L)
                .name("Upgrade to Level 4 examination")
                .description("3 Advanced BokiAI design.")
                .build();
        exam.setLevel(Level.builder().id(3L).name("Level 3").build());
        return UserExamHistory.builder()
                .id(9L)
                .createdAt(Instant.parse("2026-10-22T00:00:00Z"))
                .totalScore(4)
                .isPassed(true)
                .exam(exam)
                .build();
    }

    @Test
    void mapsExamIdFromTheExam() {
        UserExamHistoryDTO dto = LabMapper.INSTANCE.getUserOwnExamHistoryDto(history());

        assertEquals(3L, dto.getExamId());
    }

    @Test
    void mapsExamIdInThePageMappingToo() {
        List<UserExamHistoryDTO> dtos = LabMapper.INSTANCE.getUserExamHistoryDto(
                new PageImpl<>(List.of(history())));

        assertEquals(3L, dtos.get(0).getExamId());
    }
}
