package se331.bokipassport.dao;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
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
        return serviceRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public List<Service> findByLevelId(Long levelId) { return serviceRepository.findByLevel_Id(levelId); }

    @Override
    public Service findById(Long id) { return serviceRepository.findById(id).orElse(null); }

    @Override
    public Service save(Service service) { return serviceRepository.save(service); }

    @Override
    public void delete(Service service) { serviceRepository.delete(service); }
}
