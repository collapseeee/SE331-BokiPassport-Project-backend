package se331.bokipassport.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se331.bokipassport.entity.dto.UserExamHistoryDTO;
import se331.bokipassport.service.UserExamHistoryService;
import se331.bokipassport.util.LabMapper;

import java.util.List;

@RestController
@RequestMapping("/v1/exams")
@AllArgsConstructor
public class UserExamHistoryController {
    final UserExamHistoryService userExamHistoryService;

    @GetMapping("/users/{id}")
    ResponseEntity<List<UserExamHistoryDTO>> findByUserId(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "_limit", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "_page", defaultValue = "1") Integer page
    ) {
        Pageable pageRequest = PageRequest.of(page - 1, pageSize);
        HttpHeaders headers = new HttpHeaders();
        var output = userExamHistoryService.findByUserId(id, pageRequest);
        headers.set("X-Total-Count", String.valueOf(output.getTotalElements()));
        return ResponseEntity.ok().headers(headers).body(LabMapper.INSTANCE.getUserExamHistoryDto(output));
    }
}
