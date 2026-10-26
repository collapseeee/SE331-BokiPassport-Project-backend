package se331.bokipassport.dao;

import se331.bokipassport.entity.Benefit;

import java.util.List;

public interface BenefitDao {
    List<Benefit> findAll();
    List<Benefit> findByLevelId(Long levelId);
    Benefit findById(Long id);
    Benefit save(Benefit benefit);
    void delete(Benefit benefit);
}
