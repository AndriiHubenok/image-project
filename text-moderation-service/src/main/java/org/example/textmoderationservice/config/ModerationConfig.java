package org.example.textmoderationservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModerationConfig {
    public static final String RESPONSE_UPLOAD_QUEUE = "responseUpload";
    public static final String RESPONSE_MODERATE_QUEUE = "responseModerateUpload";

    @Bean(name="responseUpload")
    public Queue uploadQueue() {
        return QueueBuilder.durable(RESPONSE_UPLOAD_QUEUE).quorum().build();
    }

    @Bean(name="responseModerateUpload")
    public Queue uploadModerateQueue() {
        return QueueBuilder.durable(RESPONSE_MODERATE_QUEUE).quorum().build();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public Jackson2JsonMessageConverter jacksonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
