package se331.bokipassport.service;

import se331.bokipassport.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> findAll();
    Course findById(Long id);
}
