package se331.bokipassport.dao;

import org.springframework.data.domain.Pageable;
import se331.bokipassport.entity.UserExamHistory;

import java.util.List;

public interface UserExamHistoryDao {
    List<UserExamHistory> findByUserId(Long id, Pageable pageable);
}
