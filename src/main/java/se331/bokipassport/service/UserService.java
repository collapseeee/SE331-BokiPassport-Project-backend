package se331.bokipassport.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.bokipassport.entity.dto.UserDTO;
import se331.bokipassport.entity.dto.UserSearchOption;
import se331.bokipassport.security.user.UserAccount;

import java.util.List;

public interface UserService {
    Page<UserAccount> getUsers(Pageable pageable);
    Page<UserAccount> getUsers(UserSearchOption option, Pageable pageable);
    UserAccount findUserAccountById(Long id);
    UserAccount deactivateUser(Long id);
    UserAccount reactivateUser(Long id);
    List<String> findDistinctProvinces();
    UserAccount updateUser(Long id, UserDTO userDTO);
}
