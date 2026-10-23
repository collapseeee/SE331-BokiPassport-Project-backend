package se331.bokipassport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.bokipassport.entity.Benefit;

import java.util.List;

public interface BenefitRepository extends JpaRepository<Benefit, Long> {
    List<Benefit> findByLevel_Id(Long levelId);
}
