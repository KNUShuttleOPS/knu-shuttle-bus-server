package com.knu.shuttlebus.knu_shuttle_bus_server.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.knu.shuttlebus.knu_shuttle_bus_server.domain.Bus;
import com.knu.shuttlebus.knu_shuttle_bus_server.domain.User;
import com.knu.shuttlebus.knu_shuttle_bus_server.dto.BusRegistration;
import com.knu.shuttlebus.knu_shuttle_bus_server.dto.BusUpdateRequest;
import com.knu.shuttlebus.knu_shuttle_bus_server.repository.BusRepository;
import com.knu.shuttlebus.knu_shuttle_bus_server.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BusService {
    private final BusRepository busRepository;
    private final UserRepository userRepository;

    @Transactional
    public Bus register(BusRegistration dto){
        User targetUser = userRepository.findByDeviceId(dto.getDeviceId())
            .orElseThrow(() -> new IllegalArgumentException("Unknow User"));
        if(busRepository.findByUserId(targetUser.getUserId()).isPresent()){
            throw new IllegalArgumentException("Already Exists");
        }
        return busRepository.save(Bus.builder()
            .user(targetUser)
            .line(dto.getLine())
            .round(dto.getRound())
            .build());
    }

    @Transactional
    public Bus update(String deviceId, BusUpdateRequest dto){
        Bus targetBus = findBusByDeviceId(deviceId);
        targetBus.updateInfo(dto.getLine(), dto.getRound(), dto.getOperationInfo());
        return busRepository.save(targetBus);
    }

    @Transactional
    public Bus updateBusHeading(String deviceId, Integer heading){
        Bus targetBus = findBusByDeviceId(deviceId);
        targetBus.updateHeading(heading);
        return busRepository.save(targetBus);
    }

    @Transactional
    public Bus findBusByDeviceId(String deviceId){
        User targetUser = userRepository.findByDeviceId(deviceId)
            .orElseThrow(() -> new IllegalArgumentException("Unknow User"));
        return busRepository.findById(targetUser.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("Unknown Bus"));
    }

    public List<Bus> findBusListByLine(String line){
        return busRepository.findByLine(line);
    }
    public List<Bus> findBusesArrivingAtStop(Integer stopId){
        return busRepository.findArrivingAtStop(stopId);
    }
    public List<Bus> findAllBusList(){
        return busRepository.findByOperationInfo();
    }
}
