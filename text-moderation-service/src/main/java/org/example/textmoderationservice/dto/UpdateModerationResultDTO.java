package org.example.textmoderationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateModerationResultDTO {
    private UUID id;
    private boolean safe;
    private String verdict;
    private String reason;
}
