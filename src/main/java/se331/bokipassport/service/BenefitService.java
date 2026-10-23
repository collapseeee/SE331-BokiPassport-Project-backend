package se331.bokipassport.service;

import se331.bokipassport.entity.Benefit;

import java.util.List;

public interface BenefitService {
    List<Benefit> findAll();
    List<Benefit> findByLevelId(Long levelId);
}
