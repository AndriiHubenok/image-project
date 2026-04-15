package com.example.imageauditservice.entities;

import com.example.imageauditservice.dto.Action;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class ImageRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String filename;

    @Enumerated(EnumType.STRING)
    private Action action;

    private LocalDateTime createdAt = LocalDateTime.now();

    public ImageRecord(String filename, Action action) {
        this.filename = filename;
        this.action = action;
    }
}
