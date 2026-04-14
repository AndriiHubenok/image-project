package com.example.imageauditservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfig {
    @Bean(name="imageUpload")
    public Queue uploadQueue() {
        return QueueBuilder.durable("imageUpload").quorum().build();
    }

    @Bean(name="imageDelete")
    public Queue deleteQueue() {
        return QueueBuilder.durable("imageDelete").quorum().build();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}
