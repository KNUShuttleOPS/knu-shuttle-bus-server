package com.knu.shuttlebus.knu_shuttle_bus_server.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knu.shuttlebus.knu_shuttle_bus_server.domain.Passenger;
import com.knu.shuttlebus.knu_shuttle_bus_server.domain.User;
import com.knu.shuttlebus.knu_shuttle_bus_server.dto.UserRegistration;
import com.knu.shuttlebus.knu_shuttle_bus_server.dto.UserUpdateRequest;
import com.knu.shuttlebus.knu_shuttle_bus_server.repository.PassengerRepository;
import com.knu.shuttlebus.knu_shuttle_bus_server.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PassengerRepository passengerRepository;
    @Transactional
    public User register(UserRegistration dto){
        if(userRepository.findByDeviceId(dto.getDeviceId()).isPresent()){
            throw new IllegalArgumentException("Device Already Exists");
        }
        return userRepository.save(User.builder()
            .deviceId(dto.getDeviceId())
            .userName(dto.getUserName())
            .password(dto.getPassword())
            .build());
    }

    @Transactional
    public User update(String deviceId, UserUpdateRequest dto){
        User target = userRepository.findByDeviceId(deviceId)
            .orElseThrow(() -> new IllegalArgumentException("Unknown User"));
        target.update(dto.getUserName(), dto.getPassword());
        
        return userRepository.save(target);
    }

    @Transactional
    public void updateDeviceId(String studentId, String deviceId){
        Passenger targetPassenger = passengerRepository.findByStudentId(studentId)
            .orElse(null);
        if(targetPassenger == null){
            return;
        }

        User duplicatedtarget = userRepository.findByDeviceId(deviceId)
            .orElseThrow(() -> new IllegalArgumentException("Unknown User"));
        userRepository.delete(duplicatedtarget);
        User target = targetPassenger.getUser();
        target.updateDeviceId(deviceId);
        userRepository.save(target);
    }

    @Transactional
    public User delete(String deviceId){
        User target = findUserByDeviceId(deviceId);
        userRepository.delete(target);
        return target;
    }

    public User findUserByDeviceId(String deviceId) {
        return userRepository.findByDeviceId(deviceId)
            .orElseThrow(() -> new IllegalArgumentException("Unknown User"));
    }

    public boolean checkUserName(String userName){
        return !userRepository.existsByUserName(userName);
    }
}
