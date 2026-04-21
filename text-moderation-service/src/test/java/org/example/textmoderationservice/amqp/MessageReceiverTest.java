package org.example.textmoderationservice.amqp;

import org.example.textmoderationservice.dto.ModerationResult;
import org.example.textmoderationservice.dto.PostResponseDTO;
import org.example.textmoderationservice.service.ModerationService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MessageReceiverTest {

    @Test
    void shouldSkipNullMessage() {
        ModerationService moderationService = mock(ModerationService.class);
        MessageReceiver receiver = new MessageReceiver(moderationService);

        receiver.handleUpload(null);

        verify(moderationService, never()).moderate(org.mockito.ArgumentMatchers.anyString());
    }
}

