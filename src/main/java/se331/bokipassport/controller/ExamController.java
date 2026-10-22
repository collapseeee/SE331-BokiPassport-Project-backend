package se331.bokipassport.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se331.bokipassport.entity.dto.ExamDTO;
import se331.bokipassport.entity.dto.ExamSubmitRequest;
import se331.bokipassport.entity.dto.ExamSubmitResponse;
import se331.bokipassport.service.ExamAttemptService;
import se331.bokipassport.service.ExamService;
import se331.bokipassport.util.LabMapper;

import java.util.List;

@RestController
@RequestMapping("/v1/exams")
@AllArgsConstructor
public class ExamController {
    final ExamService examService;
    final ExamAttemptService examAttemptService;

    @GetMapping
    ResponseEntity<List<ExamDTO>> findAll() {
        return ResponseEntity.ok(LabMapper.INSTANCE.getExamDto(examService.findAll()));
    }

    @GetMapping("/{id}")
    ResponseEntity<ExamDTO> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getExamDto(examService.findById(id)));
    }

    @PostMapping("/{examId}/submit")
    ResponseEntity<ExamSubmitResponse> submit(
            @PathVariable(name = "examId") Long examId,
            @RequestBody ExamSubmitRequest request) {
        return ResponseEntity.ok(examAttemptService.submit(examId, request));
    }
}
