package se331.bokipassport.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se331.bokipassport.dao.BenefitDao;
import se331.bokipassport.entity.Benefit;

import java.util.List;

@Service
@AllArgsConstructor
public class BenefitServiceImpl implements BenefitService {
    final BenefitDao benefitDao;

    @Override
    public List<Benefit> findAll() {
        return benefitDao.findAll();
    }

    @Override
    public List<Benefit> findByLevelId(Long levelId) {
        return benefitDao.findByLevelId(levelId);
    }
}
