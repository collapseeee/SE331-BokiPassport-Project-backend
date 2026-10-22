package se331.bokipassport.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {
    Long id;
    String answerText;
    // shortcut: the answer key is sent to the browser and the frontend scores the exam client-side, upgrade to a server-scored POST /v1/exams/{id}/submit when answers must stay secret
    Boolean isCorrect;
    Long questionId;
}
