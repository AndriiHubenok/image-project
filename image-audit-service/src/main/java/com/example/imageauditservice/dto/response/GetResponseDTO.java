package com.example.imageauditservice.dto.response;

import com.example.imageauditservice.dto.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetResponseDTO {
    private UUID id;

    private String text;

    @Enumerated(EnumType.STRING)
    private Status status;
}
