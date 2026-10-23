package se331.bokipassport.service;

import se331.bokipassport.entity.Exam;

import java.util.List;

public interface ExamService {
    List<Exam> findAll();
    Exam findById(Long id);
}
