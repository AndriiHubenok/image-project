package com.example.imageauditservice.amqp;

import com.example.imageauditservice.service.ImageRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageReceiver {
    private final ImageRecordService imageRecordService;

    @RabbitListener(queues = "imageUpload")
    public void handleUpload(String message){
        System.out.println("Handling upload queue: " + message);
        if (message == null || message.isBlank()) {
            System.out.println("Skipping upload event: empty filename");
            return;
        }

        if(imageRecordService.save(message) != null){
            System.out.println("Image record saved for: " + message);
            return;
        }
        System.out.println("Failed to save image record for: " + message);
    }

    @RabbitListener(queues = "imageDelete")
    public void handleDelete(String message) {
        System.out.println("Handling delete queue: " + message);
        if (message == null || message.isBlank()) {
            System.out.println("Skipping delete event: empty filename");
            return;
        }

        if(imageRecordService.deleteByFilename(message) != null){
            System.out.println("Image record deleted for: " + message);
            return;
        }
        System.out.println("No image record found to delete for: " + message);
    }
}
