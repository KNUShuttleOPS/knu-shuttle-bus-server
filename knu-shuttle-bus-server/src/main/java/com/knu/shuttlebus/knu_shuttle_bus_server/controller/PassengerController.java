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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PassengerController {
    private final PassengerService passengerService;
    private final UserService userService;

    @PostMapping("/passengers")
    public ResponseEntity<String> register(@RequestBody PassengerRegistration passengerRegistration){
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

    @PatchMapping("/passengers/{deviceId}/station")
    public ResponseEntity<String> updateStation(
        @PathVariable String deviceId,
        @RequestParam String station
    ) {
        try {
            Passenger passenger = passengerService.updateStation(deviceId, station);
            return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("[SUCCESS] Update Passenger %s Station", passenger.getStudentId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("[FAIL] Update Passenger Station: " + e.getMessage());
        }
    }

    @PatchMapping("/passengers/{deviceId}/alarm")
    public ResponseEntity<String> updateAlarm(
        @PathVariable String deviceId,
        @RequestParam boolean alarm
    ) {
        try {
            Passenger passenger = passengerService.updateAlarm(deviceId, alarm);
            return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("[SUCCESS] Update Passenger %s Alarm %s", passenger.getStudentId(), passenger.getAlarm().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("[FAIL] Update Passenger Alarm: " + e.getMessage());
        }
    }

    @PatchMapping("/passengers/{deviceId}/token")
    public ResponseEntity<String> updateToken(
        @PathVariable String deviceId,
        @RequestParam String fcmtoken
    ) {
        try {
            Passenger passenger = passengerService.updateFCMToken(deviceId, fcmtoken);
            return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("[SUCCESS] Update Passenger %s FCMToken", passenger.getStudentId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("[FAIL] Update Passenger FCMToken: " + e.getMessage());
        }
    }

    @GetMapping("/passengers/station")
    public ResponseEntity<Object> getPassengersByStationAndAlarm(
        @RequestParam Integer station
    ) {
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
