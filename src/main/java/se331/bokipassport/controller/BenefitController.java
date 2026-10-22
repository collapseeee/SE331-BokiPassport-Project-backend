package se331.bokipassport.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
            @RequestParam(name = "levelId", required = false) Long levelId) {
        List<Benefit> benefits = levelId == null
                ? benefitService.findAll()
                : benefitService.findByLevelId(levelId);
        return ResponseEntity.ok(LabMapper.INSTANCE.getBenefitDto(benefits));
    }
}
