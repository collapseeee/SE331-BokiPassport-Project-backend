package se331.bokipassport.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserExamHistoryDTO {
    Long id;
    Instant createdAt;
    Integer totalScore;
    Boolean isPassed;
    Long examId;
    String name;
    String description;
    String level;
}
