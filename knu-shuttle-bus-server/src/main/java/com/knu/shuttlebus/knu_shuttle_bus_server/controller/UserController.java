package com.knu.shuttlebus.knu_shuttle_bus_server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.knu.shuttlebus.knu_shuttle_bus_server.domain.User;
import com.knu.shuttlebus.knu_shuttle_bus_server.dto.UserRegistration;
import com.knu.shuttlebus.knu_shuttle_bus_server.dto.UserUpdateRequest;
import com.knu.shuttlebus.knu_shuttle_bus_server.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<String> register(@RequestBody UserRegistration userRegistration){
        try {
            User user = userService.register(userRegistration);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(String.format("[SUCCESS] Register User %s", user.getUserName()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("[FAIL] Register User: " + e.getMessage());
        }
    }

    @DeleteMapping("/users/{deviceId}")
    public ResponseEntity<String> delete(
        @PathVariable String deviceId
    ) {
        try {
            User user = userService.delete(deviceId);
            return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("[SUCCESS] Delete User %s", user.getUserName()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("[FAIL] Delete User: " + e.getMessage());
        }
    }

    @PatchMapping("/users/{deviceId}")
    public ResponseEntity<String> update(
        @PathVariable String deviceId,
        @RequestBody UserUpdateRequest userUpdateRequest
    ) {
        try {
            User user = userService.update(deviceId, userUpdateRequest);
            return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("[SUCCESS] Update User %s", user.getUserName()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("[FAIL] Update User: " + e.getMessage());
        }
    }

    @GetMapping("/users/check")
    public ResponseEntity<Boolean> checkUsername(@RequestParam String username) {
        boolean isAvailable = userService.checkUserName(username);
        return ResponseEntity.ok(isAvailable);
    }
}
