package se331.bokipassport.dao;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import se331.bokipassport.entity.Course;
import se331.bokipassport.repository.CourseRepository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CourseDaoImpl implements CourseDao {
    final CourseRepository courseRepository;

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }
}
