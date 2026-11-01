package se331.bokipassport.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.bokipassport.entity.UserExamHistory;

public interface UserExamHistoryService {
    Page<UserExamHistory> findByUserId(Long id, Pageable pageable);
}
