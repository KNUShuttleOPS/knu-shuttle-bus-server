package com.knu.shuttlebus.knu_shuttle_bus_server.domain;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "passenger")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id")
    private Integer id;

    @Column(name = "student_id", nullable = false, unique = true)
    private String studentId;
    
    @Column(name = "station")
    private String station;

    @Column(name = "alarm")
    @ColumnDefault("false")
    private Boolean alarm;

    @Column(name = "fcmtoken", nullable = false)
    private String fcmToken;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;

    public void updateStation(String station){
        this.station = station;
    }
    
    public void updateAlarm(boolean alarm){
        this.alarm = alarm;
    }

    public void updateFCMToken(String fcmToken){
        this.fcmToken = fcmToken;
    }
}
