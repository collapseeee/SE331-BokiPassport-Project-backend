package se331.bokipassport.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se331.bokipassport.entity.Benefit;
import se331.bokipassport.entity.dto.BenefitDTO;
import se331.bokipassport.service.BenefitService;
import se331.bokipassport.util.LabMapper;

import java.util.List;

@RestController
@RequestMapping("/v1/benefits")
@AllArgsConstructor
public class BenefitController {
    final BenefitService benefitService;

    @GetMapping
    ResponseEntity<List<BenefitDTO>> findAll(
            @RequestParam(name = "level", required = false) Long level) {
        List<Benefit> benefits = level == null
                ? benefitService.findAll()
                : benefitService.findByLevelId(level);
        return ResponseEntity.ok(LabMapper.INSTANCE.getBenefitDto(benefits));
    }
}
