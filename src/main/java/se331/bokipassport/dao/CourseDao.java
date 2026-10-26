package se331.bokipassport.dao;

import se331.bokipassport.entity.Course;

import java.util.List;

public interface CourseDao {
    List<Course> findAll();
    Course findById(Long id);
    Course save(Course course);
}
