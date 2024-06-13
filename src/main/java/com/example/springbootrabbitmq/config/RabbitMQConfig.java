package com.example.springbootrabbitmq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
    @Value("${rabbitmq.minConcurrent}")
    private int minConcurrent;

    @Value("${rabbitmq.maxConcurrent}")
    private int maxConcurrent;

    @Bean
    public Queue queue() {
        return new Queue("success-queue", false);
    }

    @Bean
    public Exchange exchange() {
        return new DirectExchange("success-exchange");
    }

    @Bean
    public Binding binding(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with("routing-key")
                .noargs();
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }


    Jackson2JsonMessageConverter messageConverter(ObjectMapper mapper) {
        var converter = new Jackson2JsonMessageConverter(mapper);
        converter.setCreateMessageIds(true); //create a unique message id for every message
        return converter;
    }

//    @Bean
//    public SimpleRabbitListenerContainerFactory myRabbitListenerContainerFactory(
//            ConnectionFactory connectionFactory,
//            ObjectMapper objectMapper) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(messageConverter(objectMapper));
//        factory.setPrefetchCount(10);
//        factory.setConcurrentConsumers(minConcurrent);
//        factory.setMaxConcurrentConsumers(maxConcurrent);
//        return factory;
//    }
}