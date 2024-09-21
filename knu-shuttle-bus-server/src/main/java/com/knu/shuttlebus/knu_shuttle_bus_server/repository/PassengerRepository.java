package com.knu.shuttlebus.knu_shuttle_bus_server.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.knu.shuttlebus.knu_shuttle_bus_server.domain.Passenger;


@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer>{
    Optional<Passenger> findByUserId(Integer userId);
    Optional<Passenger> findByStudentId(String studentId);
    
    @Query(value = "SELECT * FROM passenger WHERE alarm = true AND station = :station", nativeQuery = true)
    List<Passenger> findByStationAndAlarm(Integer station);
}
