package se331.bokipassport.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import se331.bokipassport.entity.Answer;
import se331.bokipassport.entity.Badge;
import se331.bokipassport.entity.Benefit;
import se331.bokipassport.entity.Course;
import se331.bokipassport.entity.Exam;
import se331.bokipassport.entity.Level;
import se331.bokipassport.entity.Question;
import se331.bokipassport.entity.Service;
import se331.bokipassport.entity.UserExamHistory;
import se331.bokipassport.entity.dto.*;
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
    @Mapping(target = "isActive", source = "enabled")
    @Mapping(target = "levelId", source = "profile.levels")
    @Mapping(target = "badges", source = "profile.badges")
    @Mapping(target = "levels", source = "profile.levels")
    UserDTO getUserDto(UserAccount userAccount);

    @Mapping(target = "id", source = "profile.id")
    @Mapping(target = "firstName", source = "profile.firstName")
    @Mapping(target = "lastName", source = "profile.lastName")
    @Mapping(target = "province", source = "profile.province")
    @Mapping(target = "image", source = "profile.image")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "isActive", source = "enabled")
    @Mapping(target = "levelId", source = "profile.levels")
    @Mapping(target = "badges", source = "profile.badges")
    @Mapping(target = "levels", source = "profile.levels")
    List<UserDTO> getUserDto(List<UserAccount> userAccounts);

    default Long maxLevelId(List<Level> levels) {
        if (levels == null || levels.isEmpty()) {
            return null;
        }
        return levels.stream().map(Level::getId).max(Long::compareTo).orElse(null);
    }

    // Exams
    @Mapping(target = "name", source = "exam.name")
    @Mapping(target = "description", source = "exam.description")
    @Mapping(target = "level", source = "exam.level.name")
    List<UserExamHistoryDTO> getUserExamHistoryDto(Page<UserExamHistory> userExamHistories);
    @Mapping(target = "name", source = "exam.name")
    @Mapping(target = "description", source = "exam.description")
    @Mapping(target = "level", source = "exam.level.name")
    UserExamHistoryDTO getUserOwnExamHistoryDto(UserExamHistory userExamHistory);

    // Benefit
    @Mapping(target = "levelId", source = "level.id")
    BenefitDTO getBenefitDto(Benefit benefit);
    List<BenefitDTO> getBenefitDto(List<Benefit> benefits);
    @Mapping(target = "benefits", source = "benefits")

    // Level
    LevelDTO getLevelDto(Level level);
    List<LevelDTO> getLevelDto(List<Level> levels);

    // Badge
    List<BadgeDTO> getBadgeDto(List<Badge> badges);
    BadgeDTO getBadgeDto(Badge badge);

    // Courses
    @Mapping(target = "videoUrl", source = "video")
    @Mapping(target = "levelId", source = "level.id")
    CourseDTO getCourseDto(Course course);
    List<CourseDTO> getCourseDto(List<Course> courses);

    // Services
    @Mapping(target = "levelId", source = "level.id")
    ServiceDTO getServiceDto(Service service);
    List<ServiceDTO> getServiceDto(List<Service> services);

    // Answers & Questions
    @Mapping(target = "questionId", source = "question.id")
    AnswerDTO getAnswerDto(Answer answer);
    @Mapping(target = "examId", source = "exam.id")
    @Mapping(target = "answerChoices", source = "answerList")
    QuestionDTO getQuestionDto(Question question);
    List<QuestionDTO> getQuestionDto(List<Question> questions);

    // Exams
    @Mapping(target = "levelId", source = "level.id")
    @Mapping(target = "questionCount", source = "questions")
    ExamDTO getExamDto(Exam exam);
    List<ExamDTO> getExamDto(List<Exam> exams);

    default Integer countQuestions(List<Question> questions) {
        return questions == null ? 0 : questions.size();
    }

    // Image
    StorageFileDto getStorageFileDto(String file);
}
