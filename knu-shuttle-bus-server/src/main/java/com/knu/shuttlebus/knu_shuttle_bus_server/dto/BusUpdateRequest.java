package com.knu.shuttlebus.knu_shuttle_bus_server.dto;

import lombok.Getter;

@Getter
public class BusUpdateRequest {
    private String line;
    private Integer round;
    private Boolean operationInfo;
}
