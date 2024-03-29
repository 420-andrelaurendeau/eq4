package com.equipe4.audace.controller;

import com.equipe4.audace.controller.abstracts.GenericUserController;
import com.equipe4.audace.dto.UserDTO;
import com.equipe4.audace.dto.notification.NotificationDTO;
import com.equipe4.audace.dto.session.SessionDTO;
import com.equipe4.audace.model.User;
import com.equipe4.audace.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController extends GenericUserController<User, UserService> {
    public UserController(UserService userService) {
        super(userService);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable long id) {
        Optional<UserDTO> userOptional = service.getUser(id);

        if (userOptional.isPresent()) {
            UserDTO userDTO = userOptional.get();
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<SessionDTO>> getSessions() {
        return ResponseEntity.ok(service.getSessions());
    }

    @GetMapping("/sessions/current")
    public ResponseEntity<SessionDTO> getCurrentSession() {
        return service.getCurrentSession()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/sessions/next")
    public ResponseEntity<SessionDTO> getNextSession() {
        return service.getNextSession()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/sessions/{id}")
    public ResponseEntity<SessionDTO> getSessionById(@PathVariable Long id) {
        return service.getSessionById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @GetMapping("/notifications/{id}")
    public ResponseEntity<List<NotificationDTO>> getAllNotificationByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAllNotificationByUserId(id));
    }
    @DeleteMapping("/deleteAllNotificationsByUserId/{id}")
    public ResponseEntity<HttpStatus> deleteAllNotificationsByUserId(@PathVariable Long id) {
        service.deleteAllNotificationsByUserId(id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/deleteNotificationById/{id}")
    public ResponseEntity<HttpStatus> deleteNotificationById(@PathVariable Long id) {
        service.deleteNotificationById(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/hasNotificationByUserId/{userId}")
    public ResponseEntity<Boolean> hasNotificationByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(service.hasNotificationByUserId(userId));
    }
}