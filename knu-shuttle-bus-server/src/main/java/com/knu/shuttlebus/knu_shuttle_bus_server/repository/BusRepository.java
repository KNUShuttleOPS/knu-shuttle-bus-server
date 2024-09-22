package com.knu.shuttlebus.knu_shuttle_bus_server.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.knu.shuttlebus.knu_shuttle_bus_server.domain.Bus;

@Repository
public interface BusRepository extends JpaRepository<Bus, Integer> {
    Optional<Bus> findByUserUserId(Integer userId);

    @Query(value = "SELECT * FROM bus WHERE line = :line And operation_info = true", nativeQuery = true)
    List<Bus> findByLine(String line);

    @Query(value = "SELECT * FROM bus WHERE heading <= :station And line = :line And operation_info = true", nativeQuery = true)
    List<Bus> findArrivingAtStop(String line, Integer station);

    @Query(value = "SELECT * FROM bus WHERE operation_info = true", nativeQuery = true)
    List<Bus> findByOperationInfo();
}
