package se331.bokipassport.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerWithKeyDTO {
    Long id;
    String answerText;
    // shortcut: this DTO exposes the answer key and is served only from /v1/admin/** to ROLE_ADMIN, upgrade to a per-request key when admins must not see the answers either
    Boolean isCorrect;
    Long questionId;
}
