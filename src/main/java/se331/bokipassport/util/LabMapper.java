package se331.bokipassport.util;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import se331.bokipassport.entity.User;
import se331.bokipassport.entity.dto.UserDTO;

import java.util.List;

@Mapper
public interface LabMapper {
    LabMapper INSTANCE = Mappers.getMapper(LabMapper.class);
    List<UserDTO> getUserDto(Page<User> users);
    UserDTO getUserDto(User user);
}
