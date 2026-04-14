package com.example.imagestorageservice.amqp;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Component
public class MessageSender {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    @Qualifier("imageUpload")
    private Queue uploadQueue;

    @Autowired
    @Qualifier("imageDelete")
    private Queue deleteQueue;

    public void send(String filename) {
        this.template.convertAndSend(uploadQueue.getName(), filename);
        System.out.println(" [x] Sent '" + filename + "' to upload");
    }

    public void delete(String filename) {
        this.template.convertAndSend(deleteQueue.getName(), filename);
        System.out.println(" [x] Sent '" + filename + "' to delete");
    }
}
