package se331.bokipassport.security.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {

  Optional<UserAccount> findByEmail(String email);

  Optional<UserAccount> findByUsername(String username);

  Optional<UserAccount> findByProfile_Id(Long id);

  Page<UserAccount> findAll(Pageable pageable);

  @Query("SELECT DISTINCT ua FROM UserAccount ua " +
          "JOIN ua.profile p " +
          "LEFT JOIN p.levels l WHERE " +
          "(:name IS NULL OR :name = '' OR LOWER(p.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
          "(:province IS NULL OR :province = '' OR LOWER(p.province) LIKE LOWER(CONCAT('%', :province, '%'))) AND " +
          "(:levelId IS NULL OR (SELECT MAX(l2.id) FROM User u2 JOIN u2.levels l2 WHERE u2.id = p.id) = :levelId) AND " +
          "NOT EXISTS (SELECT r FROM ua.roles r WHERE r = se331.bokipassport.security.user.Role.ROLE_ADMIN)")
  Page<UserAccount> searchUserAccounts(
          @Param("name") String name,
          @Param("province") String province,
          @Param("levelId") Long levelId,
          Pageable pageable
  );
}
