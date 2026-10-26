package se331.bokipassport.service;

import se331.bokipassport.entity.Benefit;
import se331.bokipassport.entity.dto.BenefitDTO;

import java.util.List;

public interface BenefitService {
    List<Benefit> findAll();
    List<Benefit> findByLevelId(Long levelId);
    Benefit save(Long benefitId, BenefitDTO benefitDTO);
    List<Benefit> saveAll(Long levelId, List<BenefitDTO> benefitDTOs);
    Benefit create(Long levelId, BenefitDTO benefitDTO);
    void delete(Long id);
}
