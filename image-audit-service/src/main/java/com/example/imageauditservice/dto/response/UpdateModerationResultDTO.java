package com.example.imageauditservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateModerationResultDTO implements Serializable {
    private UUID id;
    private boolean safe;
    private String verdict;
    private String reason;
}
