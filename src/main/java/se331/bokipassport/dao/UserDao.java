package se331.bokipassport.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.bokipassport.entity.User;
import se331.bokipassport.entity.dto.UserSearchOption;
import se331.bokipassport.security.user.UserAccount;

public interface UserDao {
    Page<UserAccount> getUsers(Pageable pageable);
    Page<UserAccount> getUsers(UserSearchOption option, Pageable pageable);
    UserAccount findUserAccountById(Long id);
    UserAccount saveUser(UserAccount account);
    User saveProfile(User user);
}
