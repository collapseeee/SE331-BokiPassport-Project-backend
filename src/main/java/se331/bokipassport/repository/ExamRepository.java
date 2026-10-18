package se331.bokipassport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.bokipassport.entity.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}
