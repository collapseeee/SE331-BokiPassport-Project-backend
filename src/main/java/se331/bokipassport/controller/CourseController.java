package se331.bokipassport.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    @RolesAllowed("ADMIN")
    ResponseEntity<CourseDTO> save(@RequestBody CourseDTO course) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getCourseDto(courseService.save(course)));
    }

    @PutMapping("/{id}")
    @RolesAllowed("ADMIN")
    ResponseEntity<CourseDTO> update(@RequestBody CourseDTO course, @PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getCourseDto(courseService.update(id, course)));
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("ADMIN")
    ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        courseService.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}
