package se331.bokipassport.dao;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import se331.bokipassport.entity.User;
import se331.bokipassport.repository.UserRepository;
import se331.bokipassport.security.user.UserAccount;
import se331.bokipassport.security.user.UserAccountRepository;

@AllArgsConstructor
@Repository
public class UserDaoImpl implements UserDao {

    final UserRepository userRepository;
    final UserAccountRepository accountRepository;

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public UserAccount findUserAccountById(Long id) {
        return accountRepository.findByProfile_Id(id).stream().findFirst().orElse(null);
    }
}
