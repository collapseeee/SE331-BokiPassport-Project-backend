package se331.bokipassport.security.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserAccountDaoImpl implements UserAccountDao {
    final UserAccountRepository userAccountRepository;

    @Override
    public UserAccount findByUsername(String username) {
        return userAccountRepository.findByUsername(username).orElse(null);
    }

    @Override
    public UserAccount save(UserAccount user) {
        return userAccountRepository.save(user);
    }
}