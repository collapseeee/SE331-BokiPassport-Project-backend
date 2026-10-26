package se331.bokipassport.service;

import se331.bokipassport.entity.Course;
import se331.bokipassport.entity.dto.CourseDTO;

import java.util.List;

public interface CourseService {
    List<Course> findAll();
    Course findById(Long id);
    Course save(CourseDTO course);
}
