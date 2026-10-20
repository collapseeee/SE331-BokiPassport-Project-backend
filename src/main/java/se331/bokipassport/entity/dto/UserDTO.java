package se331.bokipassport.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    List<UserOwnExamHistoryDTO> userExamHistories;
    List<UserOwnBadgeDTO> badges;
    List<UserOwnLevelDTO> levels;
}
