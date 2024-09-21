package com.knu.shuttlebus.knu_shuttle_bus_server.dto;

import lombok.Getter;

@Getter
public class BusRegistration {
    private String deviceId;
    private String line;
    private Integer round;
}
