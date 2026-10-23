package se331.bokipassport.dao;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import se331.bokipassport.entity.Benefit;
import se331.bokipassport.repository.BenefitRepository;

import java.util.List;

@Repository
@AllArgsConstructor
public class BenefitDaoImpl implements BenefitDao {
    final BenefitRepository benefitRepository;

    @Override
    public List<Benefit> findAll() {
        return benefitRepository.findAll();
    }

    @Override
    public List<Benefit> findByLevelId(Long levelId) {
        return benefitRepository.findByLevel_Id(levelId);
    }
}
