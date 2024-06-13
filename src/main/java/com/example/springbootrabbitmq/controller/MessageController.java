package com.example.springbootrabbitmq.controller;

import com.example.springbootrabbitmq.producer.RabbitMQProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final RabbitMQProducer rabbitMQProducer;

    @GetMapping("send/message")
    public ResponseEntity<Object> sendMessage(@RequestParam("numberOfMessage") int numberOfMessage) {
        rabbitMQProducer.sendMultipleMessage("This is the message", numberOfMessage);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
