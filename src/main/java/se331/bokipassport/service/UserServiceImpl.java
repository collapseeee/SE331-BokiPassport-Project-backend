package se331.bokipassport.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se331.bokipassport.dao.UserDao;
import se331.bokipassport.entity.User;
import se331.bokipassport.security.user.UserAccount;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    final UserDao userDao;

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userDao.findAll(pageable);
    }

    @Override
    public UserAccount findUserAccountById(Long id) {
        return userDao.findUserAccountById(id);
    }
}
