package org.example.textmoderationservice.amqp;

import lombok.RequiredArgsConstructor;
import org.example.textmoderationservice.config.ModerationConfig;
import org.example.textmoderationservice.dto.ModerationResult;
import org.example.textmoderationservice.dto.PostResponseDTO;
import org.example.textmoderationservice.service.ModerationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageReceiver {
    private final ModerationService moderationService;

    @RabbitListener(queues = ModerationConfig.RESPONSE_UPLOAD_QUEUE)
    public void handleUpload(PostResponseDTO message) {
        System.out.println("Handling upload queue: " + message);
        if (message == null || message.getText() == null || message.getText().isBlank()) {
            System.out.println("Skipping upload event: empty text");
            return;
        }

        moderationService.check(message);
    }
}
