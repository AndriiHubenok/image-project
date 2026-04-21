package com.example.imageauditservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModerationResult {
    private boolean safe;
    private String verdict;
    private String reason;
}
