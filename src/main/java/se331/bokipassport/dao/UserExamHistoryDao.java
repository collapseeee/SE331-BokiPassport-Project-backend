package se331.bokipassport.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.bokipassport.entity.UserExamHistory;

public interface UserExamHistoryDao {
    Page<UserExamHistory> findByUserId(Long id, Pageable pageable);
    UserExamHistory save(UserExamHistory history);
}
