package se331.bokipassport.dao;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
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
        return benefitRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public List<Benefit> findByLevelId(Long levelId) {
        return benefitRepository.findByLevel_Id(levelId);
    }

    @Override
    public Benefit findById(Long id) {
        return benefitRepository.findById(id).orElse(null);
    }

    @Override
    public Benefit save(Benefit benefit) { return benefitRepository.save(benefit); }

    @Override
    public void delete(Benefit benefit) { benefitRepository.delete(benefit); }
}
