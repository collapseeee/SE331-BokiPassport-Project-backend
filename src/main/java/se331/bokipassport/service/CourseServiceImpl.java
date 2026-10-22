package se331.bokipassport.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se331.bokipassport.dao.CourseDao;
import se331.bokipassport.entity.Course;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
    final CourseDao courseDao;

    @Override
    public List<Course> findAll() {
        return courseDao.findAll();
    }

    @Override
    public Course findById(Long id) {
        return courseDao.findById(id);
    }
}
