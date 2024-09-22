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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Bus API", description = "버스 관련 API")
public class BusController {
    private final BusService busService;

    @Operation(summary = "버스 등록", description = "새로운 버스를 등록합니다.")
    @PostMapping("/buses")
    public ResponseEntity<String> register(
            @RequestBody BusRegistration busRegistration) {
        try {
            Bus bus = busService.register(busRegistration);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(String.format("[SUCCESS] Register Line %s Round %d Bus", bus.getLine(), bus.getRound()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("[FAIL] Register Bus: " + e.getMessage());
        }
    }

    @Operation(summary = "버스 정보 업데이트", description = "특정 디바이스 ID의 버스 정보(호선, 회차)를 업데이트합니다.")
    @PatchMapping("/buses/{deviceId}")
    public ResponseEntity<String> update(
            @Parameter(description = "디바이스 ID", required = true) @PathVariable(name = "deviceId") String deviceId,
            @RequestBody BusUpdateRequest busUpdateRequest) {
        try {
            Bus bus = busService.update(deviceId, busUpdateRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("[SUCCESS] Update Line %s Round %d Bus", bus.getLine(), bus.getRound()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("[FAIL] Update Bus : " + e.getMessage());
        }
    }

    @Operation(summary = "다음 정류장 업데이트", description = "특정 버스의 다음 정류장(행선지)을 업데이트합니다.")
    @PatchMapping("/buses/{deviceId}/heading")
    public ResponseEntity<String> updateHeading(
            @Parameter(description = "디바이스 ID", required = true) @PathVariable(name = "deviceId") String deviceId,
            @Parameter(description = "다음 정류장", required = true) @RequestParam(name = "station") Integer heading) {
        try {
            Bus bus = busService.updateBusHeading(deviceId, heading);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("[SUCCESS] Update Line %s Round %d Bus Heading", bus.getLine(),
                            bus.getRound()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("[FAIL] Update Bus Heading : " + e.getMessage());
        }
    }

    @Operation(summary = "운영 중인 모든 버스 조회", description = "운영 중인 모든 버스를 조회합니다.")
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

    @Operation(summary = "특정 노선의 버스 조회", description = "특정 노선의 모든 버스를 조회합니다.")
    @GetMapping("/buses/line")
    public ResponseEntity<Object> getbusesByLine(
            @Parameter(description = "노선 이름", required = true) @RequestParam(name = "line") String line) {
        try {
            List<Bus> buses = busService.findBusListByLine(line);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(buses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("[FAIL] Bus In Line : " + e.getMessage());
        }
    }

    @Operation(summary = "특정 정류장에 도착 예정인 버스 조회", description = "특정 정류장에 도착 예정인 버스를 조회합니다.")
    @GetMapping("/buses/arrival")
    public ResponseEntity<Object> getBusesArrivingAtStop(
            @RequestParam(name = "line") String line,
            @RequestParam(name = "station") Integer station) {
        try {
            List<Bus> buses = busService.findBusesArrivingAtStop(line, station);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(buses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("[FAIL] Bus In Heading : " + e.getMessage());
        }
    }
}
