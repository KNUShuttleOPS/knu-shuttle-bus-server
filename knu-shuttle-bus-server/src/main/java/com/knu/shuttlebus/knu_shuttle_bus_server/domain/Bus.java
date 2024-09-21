package com.knu.shuttlebus.knu_shuttle_bus_server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name = "bus")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id")
    private Integer id;

    @Column(name = "line", nullable = false)
    private String line;

    @Column(name = "round", nullable = false)
    private Integer round;

    @Column(name = "heading")
    private Integer heading;
    
    @Column(name = "operation_info")
    private Boolean operationInfo;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    public void updateInfo(String line, Integer round, Boolean operationInfo){
        this.line = line;
        this.round = round;
        this.operationInfo = operationInfo;
    }

    public void updateHeading(Integer heading){
        this.heading = heading;
    }
}
