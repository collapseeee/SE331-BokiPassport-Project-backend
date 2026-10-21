package se331.bokipassport.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.bokipassport.entity.User;
import se331.bokipassport.security.user.UserAccount;

public interface UserDao {
    Page<User> findAll(Pageable pageable);
    UserAccount findUserAccountById(Long id);
}
