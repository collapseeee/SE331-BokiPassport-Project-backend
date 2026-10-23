package se331.bokipassport.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se331.bokipassport.dao.ExamDao;
import se331.bokipassport.entity.Exam;

import java.util.List;

@Service
@AllArgsConstructor
public class ExamServiceImpl implements ExamService {
    final ExamDao examDao;

    @Override
    public List<Exam> findAll() {
        return examDao.findAll();
    }

    @Override
    public Exam findById(Long id) {
        return examDao.findById(id);
    }
}
