package se331.bokipassport.service;

import org.springframework.data.domain.Pageable;
import se331.bokipassport.entity.UserExamHistory;

import java.util.List;

public interface UserExamHistoryService {
    List<UserExamHistory> findByUserId(Long id, Pageable pageable);
}
