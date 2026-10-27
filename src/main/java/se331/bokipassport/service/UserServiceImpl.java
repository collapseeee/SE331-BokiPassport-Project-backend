package se331.bokipassport.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se331.bokipassport.dao.BadgeDao;
import se331.bokipassport.dao.UserDao;
import se331.bokipassport.entity.Badge;
import se331.bokipassport.entity.User;
import se331.bokipassport.entity.dto.UserDTO;
import se331.bokipassport.entity.dto.UserSearchOption;
import se331.bokipassport.security.user.UserAccount;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    final UserDao userDao;
    final BadgeDao badgeDao;

    @Override
    public Page<UserAccount> getUsers(Pageable pageable) {
        return userDao.getUsers(pageable);
    }

    @Override
    public Page<UserAccount> getUsers(UserSearchOption option, Pageable pageable) {
        return userDao.getUsers(option, pageable);
    }

    @Override
    public UserAccount findUserAccountById(Long id) {
        return userDao.findUserAccountById(id);
    }

    @Transactional
    @Override
    public UserAccount deactivateUser(Long id) {
        UserAccount user = userDao.findUserAccountById(id);
        user.setEnabled(false);
        return userDao.saveUser(user);
    }

    @Transactional
    @Override
    public UserAccount reactivateUser(Long id) {
        UserAccount user = userDao.findUserAccountById(id);
        user.setEnabled(true);
        return userDao.saveUser(user);
    }

    @Override
    @Transactional
    public UserAccount updateUser(Long id, UserDTO userDTO) {
        UserAccount userAccount = userDao.findUserAccountById(id);
        if (userAccount == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User with id " + id + " not found"
            );
        }

        User profile = userAccount.getProfile();
        if (profile == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User with id " + id + "'s profile not found"
            );
        }
        if (Boolean.FALSE.equals(isEmptyString(userDTO.getFirstName()))) {
            profile.setFirstName(userDTO.getFirstName());
        }
        if (Boolean.FALSE.equals(isEmptyString(userDTO.getLastName()))) {
            profile.setLastName(userDTO.getLastName());
        }
        if  (Boolean.FALSE.equals(isEmptyString(userDTO.getProvince()))) {
            profile.setProvince(userDTO.getProvince());
        }
        if (Boolean.FALSE.equals(isEmptyString(userDTO.getImage()))) {
            profile.setImage(userDTO.getImage());
        }
        return userDao.saveUser(userAccount);
    }

    @Override
    @Transactional
    public UserAccount updateUserBadge(Long id, Long badgeId) {
        if (userDao.findUserAccountById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " is not found.");
        }
        if (badgeDao.findById(badgeId) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Badge with id " + badgeId + " is not found." );
        }
        Badge badge = badgeDao.findById(badgeId);
        UserAccount user = userDao.findUserAccountById(id);
        if (user.getProfile().getBadges().stream().anyMatch(b -> b.getId().equals(badgeId))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already earn badge id " + badgeId + ".");
        }
        user.getProfile().addBadge(badge);
        badgeDao.save(badge);
        return userDao.saveUser(user);
    }

    public List<String> findDistinctProvinces() {
        return userDao.findDistinctProvinces().stream().map(User::getProvince).distinct().toList();
    }

    public Boolean isEmptyString(String string) {
        return string == null || string.isEmpty();
    }
}
