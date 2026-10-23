package se331.bokipassport.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se331.bokipassport.entity.dto.UserDTO;
import se331.bokipassport.service.UserService;
import se331.bokipassport.util.LabMapper;

@RestController
@RequestMapping("/v1/admin/users")
@AllArgsConstructor
public class AdminUserController {
    final UserService userService;

    @PatchMapping("/{id}/deactivate")
    ResponseEntity<UserDTO> deactivateUser(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getUserDto(userService.deactivateUser(id)));
    }
    @PatchMapping("/{id}/reactivate")
    ResponseEntity<UserDTO> reactivateUser(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getUserDto(userService.reactivateUser(id)));
    }

    @PutMapping("/{id}")
    // Same functionality with UserController, but for admin.
    ResponseEntity<UserDTO> updateUser(
            @PathVariable(name = "id") Long id,
            @RequestBody UserDTO userDTO
    ) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getUserDto(userService.updateUser(id, userDTO)));
    }
}
