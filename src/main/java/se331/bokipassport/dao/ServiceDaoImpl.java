package se331.bokipassport.dao;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import se331.bokipassport.entity.Service;
import se331.bokipassport.repository.ServiceRepository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ServiceDaoImpl implements ServiceDao {
    final ServiceRepository serviceRepository;

    @Override
    public List<Service> findAll() {
        return serviceRepository.findAll();
    }
}
