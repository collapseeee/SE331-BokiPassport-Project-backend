package se331.bokipassport.service;

import se331.bokipassport.entity.Service;
import se331.bokipassport.entity.dto.ServiceDTO;

import java.util.List;

public interface ServiceService {
    List<Service> findAll();
    List<Service> findByLevelId(Long levelId);
    Service create(Long levelId, ServiceDTO serviceDTO);
    Service save(Long serviceId, ServiceDTO serviceDTO);
    List<Service> saveAll(Long levelId, List<ServiceDTO> serviceDTOList);
    void delete(Long id);
    void deleteAll(List<Long> ids);
}
