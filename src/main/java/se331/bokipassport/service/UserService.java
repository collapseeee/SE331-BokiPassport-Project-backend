package se331.bokipassport.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.bokipassport.entity.dto.UserDTO;
import se331.bokipassport.entity.dto.UserSearchOption;
import se331.bokipassport.security.user.UserAccount;

public interface UserService {
    Page<UserAccount> getUsers(Pageable pageable);
    Page<UserAccount> getUsers(UserSearchOption option, Pageable pageable);
    UserAccount findUserAccountById(Long id);
    UserAccount updateUser(Long id, UserDTO userDTO);
}
