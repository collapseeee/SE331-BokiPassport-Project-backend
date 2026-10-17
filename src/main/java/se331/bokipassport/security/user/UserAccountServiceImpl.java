package se331.bokipassport.security.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
    final UserAccountDao userAccountDao;

    @Override
    @Transactional
    public UserAccount save(UserAccount user) {
        return userAccountDao.save(user);
    }

    @Override
    @Transactional
    public UserAccount findByUsername(String username) {
        return userAccountDao.findByUsername(username);
    }
}