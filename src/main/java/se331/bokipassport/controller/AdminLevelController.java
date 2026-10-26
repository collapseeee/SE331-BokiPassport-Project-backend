package se331.bokipassport.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import se331.bokipassport.entity.dto.BenefitDTO;
import se331.bokipassport.service.BenefitService;
import se331.bokipassport.util.LabMapper;

import java.util.List;

@RestController
@RequestMapping("/v1/levels")
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
/* Level Controller for admin includes Benefits, Service, and Pass Criteria of a level. */
public class AdminLevelController {
    final BenefitService benefitService;

    /* Benefits */
    @PostMapping("/{levelId}/benefits")
    ResponseEntity<List<BenefitDTO>> addBenefits(
            @PathVariable(name = "levelId") Long levelId,
            @RequestBody List<BenefitDTO> benefitDTOs
    ) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getBenefitDto(benefitService.saveAll(levelId ,benefitDTOs)));
    }
    @DeleteMapping("/benefits/{benefitId}")
    ResponseEntity<Void> deleteBenefits(
            @PathVariable(name = "benefitId")  Long benefitId
    ) {
        benefitService.delete(benefitId);
        return ResponseEntity.noContent().build();
    }
}
