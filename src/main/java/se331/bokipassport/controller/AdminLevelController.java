package se331.bokipassport.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se331.bokipassport.entity.dto.BenefitDTO;
import se331.bokipassport.entity.dto.LevelDTO;
import se331.bokipassport.entity.dto.ServiceDTO;
import se331.bokipassport.service.BenefitService;
import se331.bokipassport.service.LevelService;
import se331.bokipassport.service.ServiceService;
import se331.bokipassport.util.LabMapper;

import java.util.List;

@RestController
@RequestMapping("/v1/levels")
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
/* Level Controller for admin includes Benefits, Service, and Level itself. */
public class AdminLevelController {
    final BenefitService benefitService;
    final LevelService levelService;
    final ServiceService serviceService;

    /* Levels */
    @PutMapping("/{levelId}")
    ResponseEntity<LevelDTO> updateLevel(
            @PathVariable(name = "levelId") Long levelId,
            @RequestBody LevelDTO levelDTO
    ) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getLevelDto(levelService.save(levelId, levelDTO)));
    }

    /* Benefits */
    @PutMapping("/{levelId}/benefits")
    ResponseEntity<List<BenefitDTO>> saveBenefits(
            @PathVariable(name = "levelId") Long levelId,
            @RequestBody List<BenefitDTO> benefitDTOs
    ) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getBenefitDto(benefitService.saveAll(levelId ,benefitDTOs)));
    }
    @DeleteMapping("/benefits/{benefitId}")
    ResponseEntity<Void> deleteBenefit(
            @PathVariable(name = "benefitId")  Long benefitId
    ) {
        benefitService.delete(benefitId);
        return ResponseEntity.noContent().build();
    }

    /* Services */
    @PutMapping("/{levelId}/services")
    ResponseEntity<List<ServiceDTO>> saveServices(
            @PathVariable(name = "levelId") Long levelId,
            @RequestBody List<ServiceDTO> serviceDTOs
    ) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getServiceDto(serviceService.saveAll(levelId ,serviceDTOs)));
    }
    @DeleteMapping("/services/{serviceId}")
    ResponseEntity<Void> deleteService(
            @PathVariable(name = "serviceId") Long serviceId
    ) {
        serviceService.delete(serviceId);
        return ResponseEntity.noContent().build();
    }
}
