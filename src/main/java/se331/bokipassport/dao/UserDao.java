package se331.bokipassport.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.bokipassport.entity.User;

public interface UserDao {
    Page<User> findAll(Pageable pageable);
}
