package com.example.springbootrabbitmq.consumer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class RabbitMQConsumer {

    @RabbitListener(queues = "success-queue")
    @SneakyThrows
    public void receiveMessage(String message) 
    {
        LocalDateTime startTime = LocalDateTime.now();
        log.info("Start time: {}", startTime);

        // Handle the received message here 
        log.info("Received message: {}", message);
                    Thread.sleep(1200);

        LocalDateTime endTime = LocalDateTime.now();
        log.info("End time: {}", endTime);
    }
}