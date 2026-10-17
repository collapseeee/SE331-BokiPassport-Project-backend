package se331.bokipassport.security.user;

public interface UserDao {
    User findByUsername(String username);

    User save(User user);
}