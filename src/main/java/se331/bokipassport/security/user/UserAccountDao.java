package se331.bokipassport.security.user;

public interface UserAccountDao {
    UserAccount findByUsername(String username);

    UserAccount save(UserAccount user);
}