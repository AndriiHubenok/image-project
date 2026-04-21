package com.example.imageauditservice.amqp;

import com.example.imageauditservice.dto.response.PostResponseModerationDTO;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    @Qualifier("responseUpload")
    private Queue uploadQueue;

    public void send(PostResponseModerationDTO message) {
        this.template.convertAndSend(uploadQueue.getName(), message);
        System.out.println(" [x] Sent '" + message.getText() + "' to upload");
    }
}
