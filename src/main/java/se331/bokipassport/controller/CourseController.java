package se331.bokipassport.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se331.bokipassport.entity.dto.CourseDTO;
import se331.bokipassport.service.CourseService;
import se331.bokipassport.util.LabMapper;

import java.util.List;

@RestController
@RequestMapping("/v1/courses")
@AllArgsConstructor
public class CourseController {
    final CourseService courseService;

    @GetMapping
    ResponseEntity<List<CourseDTO>> findAll() {
        return ResponseEntity.ok(LabMapper.INSTANCE.getCourseDto(courseService.findAll()));
    }

    @GetMapping("/{id}")
    ResponseEntity<CourseDTO> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getCourseDto(courseService.findById(id)));
    }
}
