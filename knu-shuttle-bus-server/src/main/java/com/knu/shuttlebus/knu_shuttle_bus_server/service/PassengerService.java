package com.knu.shuttlebus.knu_shuttle_bus_server.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knu.shuttlebus.knu_shuttle_bus_server.domain.Passenger;
import com.knu.shuttlebus.knu_shuttle_bus_server.domain.User;
import com.knu.shuttlebus.knu_shuttle_bus_server.dto.PassengerRegistration;
import com.knu.shuttlebus.knu_shuttle_bus_server.repository.PassengerRepository;
import com.knu.shuttlebus.knu_shuttle_bus_server.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PassengerService {
    private final PassengerRepository passengerRepository;
    private final UserRepository userRepository;

    @Transactional
    public Passenger register(PassengerRegistration dto) {
        User targetUser = userRepository.findByDeviceId(dto.getDeviceId())
                .orElseThrow(() -> new IllegalArgumentException("Unknown User"));
        if (passengerRepository.findByUserId(targetUser.getUserId()).isPresent()) {
            throw new IllegalArgumentException("Passenger Already Exists");
        }

        return passengerRepository.save(Passenger.builder()
                .user(targetUser)
                .studentId(dto.getStudentId())
                .build());
    }

    @Transactional
    public Passenger updateFCMToken(String deviceId, String fcmToken) {
        Passenger targetPassenger = findPassengerByDeviceId(deviceId);
        targetPassenger.updateFCMToken(fcmToken);
        return passengerRepository.save(targetPassenger);
    }

    @Transactional
    public Passenger updateStation(String deviceId, Integer station) {
        Passenger targetPassenger = findPassengerByDeviceId(deviceId);
        targetPassenger.updateStation(station);
        return passengerRepository.save(targetPassenger);
    }

    @Transactional
    public Passenger updateAlarm(String deviceId, boolean alarm) {
        Passenger targetPassenger = findPassengerByDeviceId(deviceId);
        targetPassenger.updateAlarm(alarm);
        return passengerRepository.save(targetPassenger);
    }

    public List<Passenger> findPassengerByStationAndAlarm(Integer station) {
        return passengerRepository.findByStationAndAlarm(station);
    }

    @Transactional
    public Passenger findPassengerByDeviceId(String deviceId) {
        User targetUser = userRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("Unknown User"));
        return passengerRepository.findByUserId(targetUser.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Unknown Passenger"));
    }
}
