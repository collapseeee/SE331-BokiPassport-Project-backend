package se331.bokipassport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.bokipassport.entity.Benefit;

public interface BenefitRepository extends JpaRepository<Benefit, Long> {
}
