package se331.bokipassport.security.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {

  Optional<UserAccount> findByEmail(String email);

  Optional<UserAccount> findByUsername(String username);

}
