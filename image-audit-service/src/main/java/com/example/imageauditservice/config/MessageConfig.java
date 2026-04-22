package com.example.imageauditservice.config;

import com.example.imageauditservice.amqp.MessageSender;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class MessageConfig {
    @Bean(name="imageUpload")
    public Queue imageUploadQueue() {
        return QueueBuilder.durable("imageUpload").quorum().build();
    }

    @Bean(name="imageDelete")
    public Queue imageDeleteQueue() {
        return QueueBuilder.durable("imageDelete").quorum().build();
    }

    @Bean(name="responseUpload")
    public Queue responseUploadQueue() {
        return QueueBuilder.durable("responseUpload").quorum().build();
    }

    @Bean(name="responseModerateUpload")
    public Queue responseModerateUploadQueue() {
        return QueueBuilder.durable("responseModerateUpload").quorum().build();
    }

    @Profile("sender")
    @Bean
    public MessageSender sender() {
        return new MessageSender();
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
