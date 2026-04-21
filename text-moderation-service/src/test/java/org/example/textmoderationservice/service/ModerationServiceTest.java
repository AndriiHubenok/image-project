package org.example.textmoderationservice.service;

import org.example.textmoderationservice.amqp.MessageSender;
import org.example.textmoderationservice.dto.ModerationResult;
import org.example.textmoderationservice.mapper.ModerationResultMapper;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ModerationServiceTest {

    @Test
    void shouldReturnSafeWhenModelRespondsSafe() {
        ChatClient.Builder builder = mock(ChatClient.Builder.class);
        ChatClient chatClient = mock(ChatClient.class, RETURNS_DEEP_STUBS);
        MessageSender sender = mock(MessageSender.class);
        ModerationResultMapper mapper = mock(ModerationResultMapper.class);

        when(builder.build()).thenReturn(chatClient);
        when(chatClient.prompt().system(anyString()).user(anyString()).call().content())
                .thenReturn("SAFE|Relevant infrastructure description");

        ModerationService moderationService = new ModerationService(builder, sender, mapper);
        ModerationResult result = moderationService.moderate("Description of road and lighting conditions");

        assertTrue(result.isSafe());
        assertEquals("SAFE", result.getVerdict());
    }

    @Test
    void shouldReturnUnsafeWhenModelRespondsInUnexpectedFormat() {
        ChatClient.Builder builder = mock(ChatClient.Builder.class);
        ChatClient chatClient = mock(ChatClient.class, RETURNS_DEEP_STUBS);
        MessageSender sender = mock(MessageSender.class);
        ModerationResultMapper mapper = mock(ModerationResultMapper.class);

        when(builder.build()).thenReturn(chatClient);
        when(chatClient.prompt().system(anyString()).user(anyString()).call().content())
                .thenReturn("I think this text is spam");

        ModerationService moderationService = new ModerationService(builder, sender, mapper);
        ModerationResult result = moderationService.moderate("Buy crypto now");

        assertFalse(result.isSafe());
        assertEquals("UNSAFE", result.getVerdict());
    }
}

