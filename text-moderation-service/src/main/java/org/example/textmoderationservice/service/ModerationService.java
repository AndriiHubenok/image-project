package org.example.textmoderationservice.service;

import org.example.textmoderationservice.amqp.MessageSender;
import org.example.textmoderationservice.dto.UpdateModerationResultDTO;
import org.example.textmoderationservice.dto.ModerationResult;
import org.example.textmoderationservice.dto.PostResponseDTO;
import org.example.textmoderationservice.mapper.ModerationResultMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ModerationService {
    private static final String SYSTEM_PROMPT = """
            You are a moderator for an environmental geoinformation system.
            Analyze the description text.
            If it contains spam, insults, or is unrelated to infrastructure, return UNSAFE and a short explanation.
            If the text is relevant, return SAFE.

            Return only one line in the format:
            SAFE|short explanation
            or
            UNSAFE|short explanation
            """;
    private static final Pattern VERDICT_PATTERN = Pattern.compile("^(SAFE|UNSAFE)\\s*(?:[|:-]\\s*(.*))?$", Pattern.CASE_INSENSITIVE);

    private final ChatClient chatClient;
    private final MessageSender messageSender;
    private final ModerationResultMapper moderationResultMapper;

    public ModerationService(ChatClient.Builder builder, MessageSender messageSender, ModerationResultMapper moderationResultMapper) {
        this.chatClient = builder.build();
        this.messageSender = messageSender;
        this.moderationResultMapper = moderationResultMapper;
    }

    public ModerationResult moderate(String text) {
        if (text == null || text.isBlank()) {
            return new ModerationResult(false, "UNSAFE", "Empty text");
        }

        String rawResponse = chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .user("Text to review:\n" + text)
                .call()
                .content();

        return parseModerationResult(rawResponse);
    }

    public void check(PostResponseDTO postResponse) {
        String text = postResponse.getText();
        ModerationResult result = moderate(text);
        UpdateModerationResultDTO updateModerationResultDTO = moderationResultMapper.toModerationResultDTO(result);
        updateModerationResultDTO.setId(postResponse.getId());

        if (result.isSafe()) {
            System.out.println("Text is SAFE and accepted: " + text);
            messageSender.moderateToMainService(updateModerationResultDTO);
            return;
        }

        System.out.println("Text is UNSAFE and rejected: " + text + ". Reason: " + result.getReason());
        messageSender.moderateToMainService(updateModerationResultDTO);
    }

    private ModerationResult parseModerationResult(String rawResponse) {
        if (rawResponse == null || rawResponse.isBlank()) {
            return new ModerationResult(false, "UNSAFE", "Empty model response");
        }

        String normalized = rawResponse.strip()
                .replace("`", "")
                .lines()
                .findFirst()
                .orElse("")
                .trim();

        Matcher matcher = VERDICT_PATTERN.matcher(normalized);
        if (!matcher.matches()) {
            return new ModerationResult(false, "UNSAFE", "Invalid model response format");
        }

        String verdict = matcher.group(1).toUpperCase(Locale.ROOT);
        String reason = matcher.group(2) == null || matcher.group(2).isBlank()
                ? "No explanation"
                : matcher.group(2).trim();

        return new ModerationResult("SAFE".equals(verdict), verdict, reason);
    }
}
