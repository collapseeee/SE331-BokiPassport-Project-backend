package se331.bokipassport.dao;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import se331.bokipassport.entity.UserExamHistory;
import se331.bokipassport.repository.UserExamHistoryRepository;


@Repository
@AllArgsConstructor
public class UserExamHistoryDaoImpl implements UserExamHistoryDao {
    final UserExamHistoryRepository userExamHistoryRepository;

    @Override
    public Page<UserExamHistory> findByUserId(Long id, Pageable pageable) {
        return userExamHistoryRepository.findByUser_Id(id, pageable);
    }
}
