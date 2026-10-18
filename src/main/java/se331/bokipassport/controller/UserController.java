package se331.bokipassport.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se331.bokipassport.entity.dto.UserDTO;
import se331.bokipassport.service.UserService;
import se331.bokipassport.util.LabMapper;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    final UserService userService;

    @GetMapping
    ResponseEntity<List<UserDTO>> findAll(
            @RequestParam(name = "_limit", required = false, defaultValue = "10") Integer perPage,
            @RequestParam(name = "_page", required = false, defaultValue = "1") Integer page) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getUserDto(userService.findAll(PageRequest.of(page, perPage))));
    }
}
