package se331.bokipassport.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se331.bokipassport.dao.UserExamHistoryDao;
import se331.bokipassport.entity.UserExamHistory;

@Service
@AllArgsConstructor
public class UserExamHistoryServiceImpl implements UserExamHistoryService {
    final UserExamHistoryDao userExamHistoryDao;

    @Override
    public Page<UserExamHistory> findByUserId(Long id, Pageable pageable) {
        return userExamHistoryDao.findByUserId(id, pageable);
    }
}
