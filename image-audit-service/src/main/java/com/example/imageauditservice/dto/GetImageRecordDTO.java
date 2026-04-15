package com.example.imageauditservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetImageRecordDTO {
    private Long id;
    private String filename;
    private Action action;
    private LocalDateTime createdAt;
}
