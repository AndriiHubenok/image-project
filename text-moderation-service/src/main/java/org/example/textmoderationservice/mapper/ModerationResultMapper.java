package org.example.textmoderationservice.mapper;

import org.example.textmoderationservice.dto.UpdateModerationResultDTO;
import org.example.textmoderationservice.dto.ModerationResult;
import org.springframework.stereotype.Component;

@Component
public class ModerationResultMapper {
    public UpdateModerationResultDTO toModerationResultDTO(ModerationResult moderationResult) {
        UpdateModerationResultDTO dto = new UpdateModerationResultDTO();
        dto.setSafe(moderationResult.isSafe());
        dto.setReason(moderationResult.getReason());
        dto.setVerdict(moderationResult.getVerdict());
        return dto;
    }
}
