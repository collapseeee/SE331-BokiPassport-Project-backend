package se331.bokipassport.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import se331.bokipassport.entity.UserExamHistory;

import java.util.List;

public interface UserExamHistoryRepository extends JpaRepository<UserExamHistory, Long> {
    List<UserExamHistory> findByUser_Id(Long id, Pageable pageable);
}
