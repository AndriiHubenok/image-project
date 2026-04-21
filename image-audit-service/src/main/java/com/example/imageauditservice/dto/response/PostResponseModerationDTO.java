package com.example.imageauditservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseModerationDTO implements Serializable {
    private UUID id;
    private String text;
}
