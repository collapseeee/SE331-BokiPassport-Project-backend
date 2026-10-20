package se331.bokipassport.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se331.bokipassport.entity.dto.UserRegisterDTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {

    private String accessToken;
    private String refreshToken;

    UserRegisterDTO profile;
}
