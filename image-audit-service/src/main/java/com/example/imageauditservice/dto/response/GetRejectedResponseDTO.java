package com.example.imageauditservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRejectedResponseDTO {
    private UUID id;

    private String text;

    private String reason;
}
