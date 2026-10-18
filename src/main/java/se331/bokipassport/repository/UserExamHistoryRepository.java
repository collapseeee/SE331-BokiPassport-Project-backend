package se331.bokipassport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.bokipassport.entity.UserExamHistory;

public interface UserExamHistoryRepository extends JpaRepository<UserExamHistory, Long> {
}
