package com.example.imagestorageservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostImageResultDTO {
    private String message;
    private String filename;
    private String downloadPath;
}
