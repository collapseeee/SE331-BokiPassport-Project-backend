package se331.bokipassport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.bokipassport.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
