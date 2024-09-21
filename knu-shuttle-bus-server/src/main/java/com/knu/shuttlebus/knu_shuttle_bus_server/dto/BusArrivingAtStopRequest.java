package com.knu.shuttlebus.knu_shuttle_bus_server.dto;

import lombok.Getter;

@Getter
public class BusArrivingAtStopRequest {
    String line;
    Integer station;
}
