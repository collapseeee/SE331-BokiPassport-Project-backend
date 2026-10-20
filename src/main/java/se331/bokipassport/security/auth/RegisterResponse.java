package se331.bokipassport.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
<<<<<<< HEAD
import se331.bokipassport.entity.dto.UserRegisterDTO;
=======
import se331.bokipassport.entity.dto.UserAuthDTO;
>>>>>>> hotfix/invalid-auth-response

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {

    private String accessToken;
    private String refreshToken;

<<<<<<< HEAD
    UserRegisterDTO profile;
=======
    UserAuthDTO profile;
>>>>>>> hotfix/invalid-auth-response
}
