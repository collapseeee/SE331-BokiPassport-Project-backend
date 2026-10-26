package se331.bokipassport.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;
import se331.bokipassport.dao.LevelDao;
import se331.bokipassport.dao.ServiceDao;
import se331.bokipassport.entity.Level;
import se331.bokipassport.entity.Service;
import se331.bokipassport.entity.dto.ServiceDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// fully qualified: the entity Service and the @Service annotation share a simple name
@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceServiceImpl implements ServiceService {
    final ServiceDao serviceDao;
    final LevelDao levelDao;

    @Override
    public List<Service> findAll() {
        return serviceDao.findAll();
    }

    @Override
    public List<Service> findByLevelId(Long levelId) {
        return serviceDao.findByLevelId(levelId);
    }

    @Override
    public Service create(Long levelId, ServiceDTO serviceDTO) {
        Level level = levelDao.findById(levelId);
        if  (level == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Level with id " + levelId + " not found");
        }
        Service service = Service.builder()
                .name(serviceDTO.getName())
                .level(level)
                .build();
        return serviceDao.save(service);
    }

    @Override
    public Service save(Long serviceId, ServiceDTO serviceDTO) {
        Service service = serviceDao.findById(serviceId);
        if (service == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Service with id " + serviceId + " not found");
        }

        if (Boolean.FALSE.equals(isEmpty(serviceDTO.getName()))) {
            service.setName(serviceDTO.getName());
        }
        if (serviceDTO.getLevel() != null && !Objects.equals(serviceDTO.getLevel().getId(), service.getLevel().getId())) {
            // Change level only when not null and incoming service level differs from original
            service.setLevel(levelDao.findById(serviceDTO.getLevel().getId()));
        }
        return serviceDao.save(service);
    }

    @Override
    public List<Service> saveAll(Long levelId, List<ServiceDTO> inputServiceDTOs) {
        List<Service> savedService = new ArrayList<>();
        for (ServiceDTO serviceDTO : inputServiceDTOs) {
            if (serviceDTO.getId() != null) {
                // existed service with id.
                savedService.add(save(levelId, serviceDTO));
            } else {
                // new service without id.
                savedService.add(create(levelId, serviceDTO));
            }
        }
        return savedService;
    }

    @Override
    public void delete(Long id) {
        Service service = serviceDao.findById(id);
        if (service == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Service with id " + id + " not found");
        }
        serviceDao.delete(service);
    }

    private Boolean isEmpty(String text) {
        return text == null || text.isEmpty();
    }
}
