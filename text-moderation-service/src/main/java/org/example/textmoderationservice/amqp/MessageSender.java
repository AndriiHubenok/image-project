package org.example.textmoderationservice.amqp;

import org.example.textmoderationservice.config.ModerationConfig;
import org.example.textmoderationservice.dto.UpdateModerationResultDTO;
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
    @Qualifier(ModerationConfig.RESPONSE_MODERATE_QUEUE)
    private Queue moderateQueue;

    public void moderateToMainService(UpdateModerationResultDTO updateModerationResultDTO) {
        this.template.convertAndSend(moderateQueue.getName(), updateModerationResultDTO);
        System.out.println(" [x] Sent result to moderation response queue");
    }
}
