package se331.bokipassport.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se331.bokipassport.entity.dto.QuestionDTO;
import se331.bokipassport.security.user.UserAccount;
import se331.bokipassport.service.QuestionService;
import se331.bokipassport.util.LabMapper;

import java.util.List;

@RestController
@RequestMapping("/v1/exams")
@AllArgsConstructor
public class QuestionController {
    final QuestionService questionService;

    @GetMapping("/{examId}/questions")
    ResponseEntity<List<QuestionDTO>> findByExamId(
            @AuthenticationPrincipal UserAccount account,
            @PathVariable(name = "examId") Long examId) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getCandidateQuestionDto(
                questionService.findByExamId(examId, account)));
    }
}
