package se331.bokipassport.dao;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import se331.bokipassport.entity.UserExamHistory;
import se331.bokipassport.repository.UserExamHistoryRepository;

import java.util.List;

@Repository
@AllArgsConstructor
public class UserExamHistoryDaoImpl implements UserExamHistoryDao {
    final UserExamHistoryRepository userExamHistoryRepository;

    @Override
    public List<UserExamHistory> findByUserId(Long id, Pageable pageable) {
        return userExamHistoryRepository.findByUser_Id(id, pageable);
    }
}
