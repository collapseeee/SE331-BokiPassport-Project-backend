package se331.bokipassport.security.user;

import jakarta.transaction.Transactional;

public interface UserAccountService {
    UserAccount save(UserAccount user);

    @Transactional
    UserAccount findByUsername(String username);
}