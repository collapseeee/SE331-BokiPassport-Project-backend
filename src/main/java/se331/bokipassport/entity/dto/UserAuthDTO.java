package se331.bokipassport.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se331.bokipassport.security.user.Role;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthDTO {
    Long id;
    String firstName;
    String lastName;
    String province;
    String username;
    String image;
    List<Role> roles;
}
