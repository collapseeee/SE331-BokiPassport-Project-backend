package se331.bokipassport.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se331.bokipassport.entity.dto.ServiceDTO;
import se331.bokipassport.service.ServiceService;
import se331.bokipassport.util.LabMapper;

import java.util.List;

@RestController
@RequestMapping("/v1/services")
@AllArgsConstructor
public class ServiceController {
    final ServiceService serviceService;

    @GetMapping
    ResponseEntity<List<ServiceDTO>> findAll() {
        return ResponseEntity.ok(LabMapper.INSTANCE.getServiceDto(serviceService.findAll()));
    }

    @GetMapping("/level/{levelId}")
    ResponseEntity<List<ServiceDTO>> findByLevelId(
            @PathVariable(name = "levelId") Long levelId
    ) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getServiceDto(serviceService.findByLevelId(levelId)));
    }
}
