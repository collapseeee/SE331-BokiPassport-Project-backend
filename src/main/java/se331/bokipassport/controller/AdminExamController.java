package se331.bokipassport.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import se331.bokipassport.entity.dto.ExamDTO;
import se331.bokipassport.entity.dto.QuestionDTO;
import se331.bokipassport.security.user.UserAccount;
import se331.bokipassport.service.ExamService;
import se331.bokipassport.service.QuestionService;
import se331.bokipassport.util.LabMapper;

import java.util.List;

@RestController
@RequestMapping("/v1/exams")
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminExamController {
    final QuestionService questionService;
    final ExamService examService;

    @GetMapping("/{examId}/questionsWithKey")
    ResponseEntity<List<QuestionDTO>> findQuestionsWithKey(
            @AuthenticationPrincipal UserAccount account,
            @PathVariable(name = "examId") Long examId
    ) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getQuestionDto(
                questionService.findByExamId(examId, account)));
    }

    @PostMapping("/{examId}/questions")
    ResponseEntity<List<QuestionDTO>> addQuestions(
            @PathVariable(name = "examId") Long examId,
            @RequestBody List<QuestionDTO> questionDTO
    ) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getQuestionDto(questionService.saveAll(examId, questionDTO)));
    }

    @PutMapping("/{examId}")
    ResponseEntity<ExamDTO> updateExam(
            @PathVariable(name = "examId") Long examId,
            @RequestBody ExamDTO examDTO
    ) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getExamDto(examService.save(examId, examDTO)));
    }

    @PutMapping("/questions/{examId}")
    ResponseEntity<List<QuestionDTO>> updateQuestion(
            @PathVariable(name = "examId") Long examId,
            @RequestBody List<QuestionDTO> questionDTO
    ) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getQuestionDto(questionService.saveAll(examId, questionDTO)));
    }

    @DeleteMapping("/questions/{questionId}")
    ResponseEntity<Void> deleteQuestion(
            @PathVariable(name = "questionId") Long questionId
    ) {
        questionService.delete(questionId);
        return ResponseEntity.noContent().build();
    }
}
