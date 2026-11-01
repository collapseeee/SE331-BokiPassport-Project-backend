package se331.bokipassport.dao;

import se331.bokipassport.entity.Service;

import java.util.List;

public interface ServiceDao {
    List<Service> findAll();
    List<Service> findByLevelId(Long levelId);
    Service findById(Long id);
    Service save(Service service);
    void delete(Service service);
}
