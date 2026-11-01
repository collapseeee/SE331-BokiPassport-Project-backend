package se331.bokipassport.service;

import se331.bokipassport.entity.Exam;
import se331.bokipassport.entity.dto.ExamDTO;

import java.util.List;

public interface ExamService {
    List<Exam> findAll();
    Exam findById(Long id);
    Exam save(Long id, ExamDTO examDTO);
}
