package se331.bokipassport.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import se331.bokipassport.entity.dto.LevelDTO;
import se331.bokipassport.service.LevelService;
import se331.bokipassport.util.LabMapper;

import java.util.List;

@Repository
@RequestMapping("/v1/levels")
@AllArgsConstructor
public class LevelController {
    final LevelService levelService;

    @GetMapping
    ResponseEntity<List<LevelDTO>> findAll() {
        return ResponseEntity.ok(LabMapper.INSTANCE.getLevelDto(levelService.findAll()));
    }

    @GetMapping("/{id}")
    ResponseEntity<LevelDTO> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getLevelDto(levelService.findById(id)));
    }
}
