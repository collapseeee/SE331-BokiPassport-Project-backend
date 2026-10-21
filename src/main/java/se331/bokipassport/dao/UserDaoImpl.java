package se331.bokipassport.dao;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import se331.bokipassport.entity.dto.UserSearchOption;
import se331.bokipassport.repository.UserRepository;
import se331.bokipassport.security.user.UserAccount;
import se331.bokipassport.security.user.UserAccountRepository;

@AllArgsConstructor
@Repository
public class UserDaoImpl implements UserDao {

    final UserRepository userRepository;
    final UserAccountRepository accountRepository;

    @Override
    public Page<UserAccount> getUsers(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public Page<UserAccount> getUsers(UserSearchOption option, Pageable pageable) {
        return accountRepository.searchUserAccounts(option.getName(), option.getProvince(), option.getLevel(), pageable);
    }
    /* End of User Search + Filtering */

    @Override
    public UserAccount findUserAccountById(Long id) {
        return accountRepository.findByProfile_Id(id).stream().findFirst().orElse(null);
    }

}
