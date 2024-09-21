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

import com.knu.shuttlebus.knu_shuttle_bus_server.domain.Bus;
import com.knu.shuttlebus.knu_shuttle_bus_server.dto.BusRegistration;
import com.knu.shuttlebus.knu_shuttle_bus_server.dto.BusUpdateRequest;
import com.knu.shuttlebus.knu_shuttle_bus_server.service.BusService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BusController {
    private final BusService busService;
    
    @PostMapping("/buses")
    public ResponseEntity<String> register(@RequestBody BusRegistration busRegistration){
        try {
            Bus bus = busService.register(busRegistration);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(String.format("[SUCCESS] Register Line %s Round %d Bus", bus.getLine(), bus.getRound()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("[FAIL] Register Bus: " + e.getMessage());
        }
    }

    @PatchMapping("/buses/{deviceId}")
    public ResponseEntity<String> update(
        @PathVariable String deviceId,
        @RequestBody BusUpdateRequest busUpdateRequest
    ) {
        try {
            Bus bus = busService.update(deviceId, busUpdateRequest);
            return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("[SUCCESS] Update Line %s Round %d Bus", bus.getLine(), bus.getRound()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("[FAIL] Update Bus : " + e.getMessage());
        }
    }

    @PatchMapping("/buses/{deviceId}/heading")
    public ResponseEntity<String> updateHeading(
        @PathVariable String deviceId,
        @RequestParam Integer heading
    ) {
        try {
            Bus bus = busService.updateBusHeading(deviceId, heading);
            return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("[SUCCESS] Update Line %s Round %d Bus Heading", bus.getLine(), bus.getRound()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("[FAIL] Update Bus Heading : " + e.getMessage());
        }
    }
    
    @GetMapping("/buses/operating")
    public ResponseEntity<Object> getbuses() {
        try {
            List<Bus> buses = busService.findAllBusList();
            
            return ResponseEntity.status(HttpStatus.OK)
                .body(buses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("[FAIL] All Bus : " + e.getMessage());
        }
    }

    @GetMapping("/buses/line")
    public ResponseEntity<Object> getbusesByLine(
        @RequestParam String line
    ) {
        try {
            List<Bus> buses = busService.findBusListByLine(line);
            
            return ResponseEntity.status(HttpStatus.OK)
                .body(buses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("[FAIL] Bus In Line : " + e.getMessage());
        }
    }
    
    @GetMapping("/buses/arrival")
    public ResponseEntity<Object> getBusesArrivingAtStop(
        @RequestParam Integer stopId
    ) {
        try {
            List<Bus> buses = busService.findBusesArrivingAtStop(stopId);
            
            return ResponseEntity.status(HttpStatus.OK)
                .body(buses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("[FAIL] Bus In Heading : " + e.getMessage());
        }
    }
}   
