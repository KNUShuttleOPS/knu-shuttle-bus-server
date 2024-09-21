package com.knu.shuttlebus.knu_shuttle_bus_server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.knu.shuttlebus.knu_shuttle_bus_server.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByDeviceId(String deviceId);
    boolean existsByUserName(String userName);
}
