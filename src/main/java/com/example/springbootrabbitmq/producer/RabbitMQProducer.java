package com.example.springbootrabbitmq.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQProducer { 

    private final RabbitTemplate rabbitTemplate;
  
    public void sendMessage(String message) 
    { 
        rabbitTemplate.convertAndSend( 
            "message-exchange", "routing-key", message);
    }

    public void sendMultipleMessage(String message, int numberOfMessage) {
        // Create a thread pool to send messages concurrently
        ExecutorService executor = Executors.newFixedThreadPool(numberOfMessage);

        // Send messages concurrently
        for (int i = 0; i < numberOfMessage; i++) {
            int finalI = i + 1;
            executor.submit(() -> rabbitTemplate.convertAndSend("success-exchange", "routing-key", message + finalI));
        }

        // Wait for all tasks to complete
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}