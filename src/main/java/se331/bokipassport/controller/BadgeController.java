package se331.bokipassport.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se331.bokipassport.entity.dto.BadgeDTO;
import se331.bokipassport.service.BadgeService;
import se331.bokipassport.util.LabMapper;

import java.util.List;

@RestController
@RequestMapping("/v1/badges")
@AllArgsConstructor
public class BadgeController {
    final BadgeService badgeService;

    @GetMapping
    ResponseEntity<List<BadgeDTO>> findAll() {
        return ResponseEntity.ok(LabMapper.INSTANCE.getBadgeDto(badgeService.findAll()));
    }

    @GetMapping("/{id}")
    ResponseEntity<BadgeDTO> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getBadgeDto(badgeService.findById(id)));
    }

    @GetMapping("/users/{id}")
    ResponseEntity<List<BadgeDTO>> findByUserId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getBadgeDto(badgeService.findByUserId(id)));
    }
}
