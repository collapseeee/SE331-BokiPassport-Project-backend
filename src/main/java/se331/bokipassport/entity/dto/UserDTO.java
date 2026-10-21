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
public class UserDTO {
    Long id;
    String firstName;
    String lastName;
    String province;
    String image;
    String username;
    Boolean isActive;

    List<Role> roles;
    List<UserOwnBadgeDTO> badges;
    List<UserOwnLevelDTO> levels;
}
