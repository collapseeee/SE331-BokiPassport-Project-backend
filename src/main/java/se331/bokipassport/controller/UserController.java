package se331.bokipassport.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se331.bokipassport.entity.dto.UserDTO;
import se331.bokipassport.entity.dto.UserSearchOption;
import se331.bokipassport.security.user.UserAccount;
import se331.bokipassport.service.UserService;
import se331.bokipassport.util.LabMapper;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UserController {
    final UserService userService;

    @GetMapping
    ResponseEntity<List<UserDTO>> findAll(
            @RequestParam(name = "_limit", required = false, defaultValue = "10") Integer perPage,
            @RequestParam(name = "_page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "level", required = false) Long level,
            @RequestParam(name = "search", required = false) String name,
            @RequestParam(name = "province", required = false) String province
    ) {
        Page<UserAccount> output;
        Pageable pageable = PageRequest.of(page - 1, perPage);
        if (level != null || name != null || province != null) {
            UserSearchOption option = UserSearchOption.builder()
                    .name(name)
                    .province(province)
                    .level(level)
                    .build();
            output = userService.getUsers(option, pageable);
        } else {
            output = userService.getUsers(pageable);
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("X-Total-Count", String.valueOf(output.getTotalElements()));
        return ResponseEntity.ok().headers(responseHeaders).body(LabMapper.INSTANCE.getUserDto(output.getContent()));
    }

    @GetMapping("/{id}")
    ResponseEntity<UserDTO> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getUserDto(userService.findUserAccountById(id)));
    }

    @PatchMapping("/{id}/deactivate")
    ResponseEntity<UserDTO> deactivateUser(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getUserDto(userService.deactivateUser(id)));
    }
    @PatchMapping("/{id}/reactivate")
    ResponseEntity<UserDTO> reactivateUser(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(LabMapper.INSTANCE.getUserDto(userService.reactivateUser(id)));
    }
}
