package se331.bokipassport.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se331.bokipassport.entity.dto.QuestionDTO;
import se331.bokipassport.service.QuestionService;
import se331.bokipassport.util.LabMapper;

import java.util.List;

@RestController
@RequestMapping("/v1/questions")
@AllArgsConstructor
public class QuestionController {
    final QuestionService questionService;

    @GetMapping
    ResponseEntity<List<QuestionDTO>> findByExamId(@RequestParam(name = "examId") Long examId) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getQuestionDto(questionService.findByExamId(examId)));
    }
}
