package se331.bokipassport.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamSubmitResponse {
    Long examId;
    Integer totalScore;
    Integer maxScore;
    Boolean isPassed;
    Long awardedLevelId;
}
