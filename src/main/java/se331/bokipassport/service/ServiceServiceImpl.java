package se331.bokipassport.service;

import lombok.AllArgsConstructor;
import se331.bokipassport.dao.ServiceDao;
import se331.bokipassport.entity.Service;

import java.util.List;

// fully qualified: the entity Service and the @Service annotation share a simple name
@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceServiceImpl implements ServiceService {
    final ServiceDao serviceDao;

    @Override
    public List<Service> findAll() {
        return serviceDao.findAll();
    }
}
