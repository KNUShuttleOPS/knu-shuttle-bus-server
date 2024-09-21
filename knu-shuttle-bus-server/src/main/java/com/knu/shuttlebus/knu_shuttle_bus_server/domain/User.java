package com.knu.shuttlebus.knu_shuttle_bus_server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;
    
    @Column(name = "device_id", nullable = false, unique = true)
    private String deviceId;

    public void update(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public void updateDeviceId(String deviceId){
        this.deviceId = deviceId;
    }
}
