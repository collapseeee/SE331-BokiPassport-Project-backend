package se331.bokipassport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.bokipassport.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
