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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Tag(name = "User API", description = "사용자 관련 API")
public class UserController {
    private final UserService userService;

    @Operation(summary = "사용자 등록", description = "새로운 사용자를 등록합니다.")
    @PostMapping("/users")
    public ResponseEntity<String> register(
            @Parameter(description = "등록할 사용자 정보", required = true) @RequestBody UserRegistration userRegistration) {
        try {
            User user = userService.register(userRegistration);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(String.format("[SUCCESS] Register User %s", user.getUserName()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("[FAIL] Register User: " + e.getMessage());
        }
    }

    @Operation(summary = "사용자 삭제", description = "특정 사용자 정보를 삭제합니다.")
    @DeleteMapping("/users/{deviceId}")
    public ResponseEntity<String> delete(
            @Parameter(description = "삭제할 사용자의 디바이스 ID", required = true) @PathVariable String deviceId) {
        try {
            User user = userService.delete(deviceId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("[SUCCESS] Delete User %s", user.getUserName()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("[FAIL] Delete User: " + e.getMessage());
        }
    }

    @Operation(summary = "사용자 정보 업데이트", description = "특정 사용자의 정보를 업데이트합니다.")
    @PatchMapping("/users/{deviceId}")
    public ResponseEntity<String> update(
            @Parameter(description = "업데이트할 사용자의 디바이스 ID", required = true) @PathVariable String deviceId,
            @Parameter(description = "업데이트할 사용자 정보", required = true) @RequestBody UserUpdateRequest userUpdateRequest) {
        try {
            User user = userService.update(deviceId, userUpdateRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("[SUCCESS] Update User %s", user.getUserName()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("[FAIL] Update User: " + e.getMessage());
        }
    }

    @Operation(summary = "사용자 이름 중복 체크", description = "사용자 이름의 중복 여부를 확인합니다.")
    @GetMapping("/users/check")
    public ResponseEntity<Boolean> checkUsername(
            @Parameter(description = "체크할 사용자 이름", required = true) @RequestParam String username) {
        boolean isAvailable = userService.checkUserName(username);
        return ResponseEntity.ok(isAvailable);
    }
}
