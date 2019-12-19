package ru.photorex.hw16.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.photorex.hw16.model.User;
import ru.photorex.hw16.to.RegUser;

import java.util.List;

@Controller
@RestControllerEndpoint(id = "users")
@RequiredArgsConstructor
public class ActuatorController {

    private final ActuatorUserService actuatorUserService;

    @GetMapping
    public List<User> getAll() {
        return actuatorUserService.getAllUsers();
    }

    @PostMapping("/admin")
    public ResponseEntity<User> createAdminAccount(@RequestBody RegUser regUser) {
        return ResponseEntity.ok(actuatorUserService.createAdmin(regUser));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> changeEnabled(@PathVariable String id,
                                              @RequestParam(value = "enabled", required = false, defaultValue = "false") boolean isEnabled,
                                              @RequestParam(value = "credentials", required = false, defaultValue = "false") boolean isCredentialsNonExpired,
                                              @RequestParam(value = "accLocked", required = false, defaultValue = "false") boolean isAccountNonLocked,
                                              @RequestParam(value = "accExpired", required = false, defaultValue = "false") boolean isAccountNonExpired) {

        return ResponseEntity.ok(actuatorUserService.changeAccount(id, isEnabled, isCredentialsNonExpired, isAccountNonLocked, isAccountNonExpired));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteAccount(@PathVariable String id) {
        actuatorUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
