package org.example.textmoderationservice.controller;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.example.textmoderationservice.dto.ModerationResult;
import org.example.textmoderationservice.dto.PostResponseDTO;
import org.example.textmoderationservice.service.ModerationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ModerationController {
    private final ModerationService moderationService;

    @PostMapping("/moderate")
    public ModerationResult moderateText(@Valid @RequestBody PostResponseDTO dto) {
        return moderationService.moderate(dto.getText());
    }
}
