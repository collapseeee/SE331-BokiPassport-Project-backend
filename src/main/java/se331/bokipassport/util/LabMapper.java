package se331.bokipassport.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import se331.bokipassport.entity.User;
import se331.bokipassport.entity.UserExamHistory;
import se331.bokipassport.entity.dto.UserDTO;
import se331.bokipassport.entity.dto.UserOwnExamHistoryDTO;
import se331.bokipassport.entity.dto.UserRegisterDTO;

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

    UserRegisterDTO getUserRegisterDto(User user);
}
