package com.knu.shuttlebus.knu_shuttle_bus_server.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.knu.shuttlebus.knu_shuttle_bus_server.domain.Passenger;
import com.knu.shuttlebus.knu_shuttle_bus_server.dto.PassengerRegistration;
import com.knu.shuttlebus.knu_shuttle_bus_server.service.PassengerService;
import com.knu.shuttlebus.knu_shuttle_bus_server.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Passenger API", description = "승객 관련 API")
public class PassengerController {
    private final PassengerService passengerService;
    private final UserService userService;

    @Operation(summary = "승객 등록", description = "새로운 승객을 등록합니다. 이미 같은 학번의 승객이 존재할 경우 기존의 정보를 넘겨 받습니다.")
    @PostMapping("/passengers")
    public ResponseEntity<String> register(
            @RequestBody PassengerRegistration passengerRegistration) {
        try {
            userService.updateDeviceId(passengerRegistration.getStudentId(), passengerRegistration.getDeviceId());
            Passenger passenger = passengerService.register(passengerRegistration);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(String.format("[SUCCESS] Register Passenger %s", passenger.getStudentId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("[FAIL] Register Passenger : " + e.getMessage());
        }
    }

    @Operation(summary = "승객의 정류장 업데이트", description = "특정 승객의 정류장 정보를 업데이트합니다.")
    @PatchMapping("/passengers/{deviceId}/station")
    public ResponseEntity<String> updateStation(
            @Parameter(description = "승객의 디바이스 ID", required = true) @PathVariable(name = "deviceId") String deviceId,
            @Parameter(description = "업데이트할 정류장 번호", required = true) @RequestParam(name = "station") Integer station) {
        try {
            Passenger passenger = passengerService.updateStation(deviceId, station);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("[SUCCESS] Update Passenger %s Station", passenger.getStudentId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("[FAIL] Update Passenger Station: " + e.getMessage());
        }
    }

    @Operation(summary = "승객의 알람 상태 업데이트", description = "특정 승객의 알람 상태를 업데이트합니다.")
    @PatchMapping("/passengers/{deviceId}/alarm")
    public ResponseEntity<String> updateAlarm(
            @Parameter(description = "승객의 디바이스 ID", required = true) @PathVariable(name = "deviceId") String deviceId,
            @Parameter(description = "알람 활성화 여부", required = true) @RequestParam(name = "alarm") boolean alarm) {
        try {
            Passenger passenger = passengerService.updateAlarm(deviceId, alarm);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("[SUCCESS] Update Passenger %s Alarm %s", passenger.getStudentId(),
                            passenger.getAlarm().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("[FAIL] Update Passenger Alarm: " + e.getMessage());
        }
    }

    @Operation(summary = "승객의 FCM 토큰 업데이트", description = "특정 승객의 FCM 토큰을 업데이트합니다.")
    @PatchMapping("/passengers/{deviceId}/token")
    public ResponseEntity<String> updateToken(
            @Parameter(description = "승객의 디바이스 ID", required = true) @PathVariable(name = "deviceId") String deviceId,
            @Parameter(description = "업데이트할 FCM 토큰", required = true) @RequestParam(name = "fcmtoken") String fcmtoken) {
        try {
            Passenger passenger = passengerService.updateFCMToken(deviceId, fcmtoken);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("[SUCCESS] Update Passenger %s FCMToken", passenger.getStudentId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("[FAIL] Update Passenger FCMToken: " + e.getMessage());
        }
    }

    @Operation(summary = "특정 정류장의 승객 조회", description = "특정 정류장에서 알람이 설정된 승객들을 조회합니다.")
    @GetMapping("/passengers/station")
    public ResponseEntity<Object> getPassengersByStationAndAlarm(
            @Parameter(description = "정류장 번호", required = true) @RequestParam(name = "station") Integer station) {
        try {
            List<Passenger> passengers = passengerService.findPassengerByStationAndAlarm(station);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(passengers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("[FAIL] All Bus : " + e.getMessage());
        }
    }
}