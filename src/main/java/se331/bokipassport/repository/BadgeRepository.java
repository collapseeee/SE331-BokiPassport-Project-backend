package se331.bokipassport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.bokipassport.entity.Badge;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
}
