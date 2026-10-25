package se331.bokipassport.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    // present only for the admin view; the candidate view leaves it null and @JsonInclude drops the key
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Boolean isCorrect;
}
