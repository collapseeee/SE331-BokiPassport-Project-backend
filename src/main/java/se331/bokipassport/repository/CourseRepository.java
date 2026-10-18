package se331.bokipassport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.bokipassport.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
