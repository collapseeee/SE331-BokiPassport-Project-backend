package se331.bokipassport.dao;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import se331.bokipassport.entity.Exam;
import se331.bokipassport.repository.ExamRepository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ExamDaoImpl implements ExamDao {
    final ExamRepository examRepository;

    @Override
    public List<Exam> findAll() {
        return examRepository.findAll();
    }

    @Override
    public Exam findById(Long id) {
        return examRepository.findById(id).orElse(null);
    }

    @Override
    public Exam save(Exam exam) {
        return examRepository.save(exam);
    }
}
