package se331.bokipassport.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import se331.bokipassport.entity.Exam;
import se331.bokipassport.entity.dto.ExamDTO;
import se331.bokipassport.entity.dto.QuestionDTO;
import se331.bokipassport.security.user.UserAccount;
import se331.bokipassport.service.ExamService;
import se331.bokipassport.service.QuestionService;
import se331.bokipassport.util.LabMapper;

import java.util.List;

@RestController
@RequestMapping("/v1/admin/exams")
@AllArgsConstructor
public class AdminExamController {
    final QuestionService questionService;
    final ExamService examService;

    @GetMapping("/{examId}/questions")
    ResponseEntity<List<QuestionDTO>> findQuestionsWithKey(
            @AuthenticationPrincipal UserAccount account,
            @PathVariable(name = "examId") Long examId) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getQuestionDto(
                questionService.findByExamId(examId, account)));
    }

    @PutMapping("/{examId}")
    ResponseEntity<ExamDTO> updateExam(
            @PathVariable(name = "examId") Long examId,
            @RequestBody ExamDTO examDTO
    ) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getExamDto(examService.save(examId, examDTO)));
    }

    @PutMapping("/questions/{questionId}")
    ResponseEntity<QuestionDTO> updateQuestion(
            @PathVariable(name = "questionId") Long questionId,
            @RequestBody QuestionDTO questionDTO
    ) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getQuestionDto(questionService.updateQuestion(questionId, questionDTO)));
    }

    @DeleteMapping("/questions/{questionId}")
    ResponseEntity<String> deleteQuestion(
            @PathVariable(name = "questionId") Long questionId
    ) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok("Removed question with id " + questionId + " successfully");
    }
}
