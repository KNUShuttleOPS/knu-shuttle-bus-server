package com.knu.shuttlebus.knu_shuttle_bus_server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.knu.shuttlebus.knu_shuttle_bus_server.domain.Passenger;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FCMService {
    private final FirebaseMessaging firebaseMessaging;

    public void sendMessageToPassengers(List<Passenger> passengers, String title, String body){
        passengers.stream()
            .forEach(passenger -> sendMessageByToken(passenger.getFcmToken(), title, body));
    }

    public void sendMessageByToken(String targetToken, String title, String body) {
        try {
            firebaseMessaging.send(
                Message.builder()
                    .putData("title", title)
                    .putData("body", body)
                    .setToken(targetToken)
                    .build()
            );
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }
}
