package com.example.imageauditservice.entities;

import com.example.imageauditservice.dto.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String text;

    @Enumerated(EnumType.STRING)
    private Status status;
}
