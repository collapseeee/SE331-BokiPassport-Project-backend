package se331.bokipassport.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import se331.bokipassport.entity.Benefit;
import se331.bokipassport.entity.Level;
import se331.bokipassport.entity.UserExamHistory;
import se331.bokipassport.entity.dto.BenefitDTO;
import se331.bokipassport.entity.dto.LevelDTO;
import se331.bokipassport.entity.dto.UserDTO;
import se331.bokipassport.entity.dto.UserExamHistoryDTO;
import se331.bokipassport.security.user.UserAccount;

import java.util.List;

@Mapper
public interface LabMapper {
    LabMapper INSTANCE = Mappers.getMapper(LabMapper.class);

    /* Nested DTO Mapping */
    // UserAccount to UserDTO
    @Mapping(target = "id", source = "profile.id")
    @Mapping(target = "firstName", source = "profile.firstName")
    @Mapping(target = "lastName", source = "profile.lastName")
    @Mapping(target = "province", source = "profile.province")
    @Mapping(target = "image", source = "profile.image")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "enabled", source = "enabled")
    @Mapping(target = "badges", source = "profile.badges")
    @Mapping(target = "levels", source = "profile.levels")
    UserDTO getUserDto(UserAccount userAccount);

    @Mapping(target = "id", source = "profile.id")
    @Mapping(target = "firstName", source = "profile.firstName")
    @Mapping(target = "lastName", source = "profile.lastName")
    @Mapping(target = "province", source = "profile.province")
    @Mapping(target = "image", source = "profile.image")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "badges", source = "profile.badges")
    @Mapping(target = "levels", source = "profile.levels")
    List<UserDTO> getUserDto(List<UserAccount> userAccounts);

    // Exams
    @Mapping(target = "name", source = "exam.name")
    @Mapping(target = "description", source = "exam.description")
    @Mapping(target = "level", source = "exam.level.name")
    List<UserExamHistoryDTO> getUserExamHistoryDto(Page<UserExamHistory> userExamHistories);
    @Mapping(target = "name", source = "exam.name")
    @Mapping(target = "description", source = "exam.description")
    @Mapping(target = "level", source = "exam.level.name")
    UserExamHistoryDTO getUserOwnExamHistoryDto(UserExamHistory userExamHistory);
    @Mapping(target = "levelId", source = "level.id")
    BenefitDTO getBenefitDto(Benefit benefit);
    List<BenefitDTO> getBenefitDto(List<Benefit> benefits);
    @Mapping(target = "benefits", source = "benefits")
    LevelDTO getLevelDto(Level level);
    List<LevelDTO> getLevelDto(List<Level> levels);

    // Image
    StorageFileDto getStorageFileDto(String file);
}
