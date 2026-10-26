package se331.bokipassport.service;

import se331.bokipassport.entity.Benefit;
import se331.bokipassport.entity.dto.BenefitDTO;

import java.util.List;

public interface BenefitService {
    List<Benefit> findAll();
    List<Benefit> findByLevelId(Long levelId);
    Benefit save(Long benefitId, BenefitDTO benefitDTO);
    Benefit create(Long levelId, BenefitDTO benefitDTO);
}
