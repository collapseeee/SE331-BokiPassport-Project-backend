package se331.bokipassport.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import se331.bokipassport.entity.User;
import se331.bokipassport.entity.UserExamHistory;
import se331.bokipassport.entity.dto.UserDTO;
import se331.bokipassport.entity.dto.UserOwnExamHistoryDTO;
<<<<<<< HEAD
import se331.bokipassport.entity.dto.UserRegisterDTO;
=======
import se331.bokipassport.entity.dto.UserAuthDTO;
>>>>>>> hotfix/invalid-auth-response
import se331.bokipassport.security.user.UserAccount;

import java.util.List;

@Mapper
public interface LabMapper {
    LabMapper INSTANCE = Mappers.getMapper(LabMapper.class);

    /* Parent DTO Mapping */
    List<UserDTO> getUserDto(Page<User> users);
    UserDTO getUserDto(User user);

    /* Nested DTO Mapping */
    List<UserOwnExamHistoryDTO> getUserOwnExamHistoryDto(List<UserExamHistory> userExamHistories);
    @Mapping(target = "name", source = "exam.name")
    @Mapping(target = "description", source = "exam.description")
    @Mapping(target = "level", source = "exam.level.name")
    UserOwnExamHistoryDTO getUserOwnExamHistoryDto(UserExamHistory userExamHistory);

    @Mapping(target = "id", source = "profile.id")
    @Mapping(target = "firstName", source = "profile.firstName")
    @Mapping(target = "lastName", source = "profile.lastName")
    @Mapping(target = "province", source = "profile.province")
    @Mapping(target = "image", source = "profile.image")
    @Mapping(target = "roles", source = "roles")
<<<<<<< HEAD
    UserRegisterDTO getUserRegisterDto(UserAccount userAccount);
=======
    UserAuthDTO getUserAuthDto(UserAccount userAccount);
>>>>>>> hotfix/invalid-auth-response
}
