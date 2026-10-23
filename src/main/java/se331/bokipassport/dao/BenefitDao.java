package se331.bokipassport.dao;

import se331.bokipassport.entity.Benefit;

import java.util.List;

public interface BenefitDao {
    List<Benefit> findAll();
    List<Benefit> findByLevelId(Long levelId);
}
