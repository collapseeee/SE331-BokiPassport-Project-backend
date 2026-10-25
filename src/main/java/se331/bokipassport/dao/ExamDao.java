package se331.bokipassport.dao;

import se331.bokipassport.entity.Exam;

import java.util.List;

public interface ExamDao {
    List<Exam> findAll();
    Exam findById(Long id);
    Exam save(Exam exam);
}
