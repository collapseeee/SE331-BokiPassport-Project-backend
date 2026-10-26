package se331.bokipassport.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se331.bokipassport.dao.CourseDao;
import se331.bokipassport.dao.LevelDao;
import se331.bokipassport.entity.Course;
import se331.bokipassport.entity.Level;
import se331.bokipassport.entity.dto.CourseDTO;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
    final CourseDao courseDao;
    final LevelDao levelDao;

    @Override
    public List<Course> findAll() {
        return courseDao.findAll();
    }

    @Override
    public Course findById(Long id) {
        return courseDao.findById(id);
    }

    @Transactional
    @Override
    public Course save(CourseDTO course) {
        if (course.getLevel() == null || course.getLevel().getId() == null || levelDao.findById(course.getLevel().getId()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Level id is not found.");
        }
        Level level = levelDao.findById(course.getLevel().getId());
        Course output = Course.builder()
                .name(course.getName())
                .description(course.getDescription())
                .video(course.getVideoUrl())
                .level(level)
                .build();

        return courseDao.save(output);
    }
}
