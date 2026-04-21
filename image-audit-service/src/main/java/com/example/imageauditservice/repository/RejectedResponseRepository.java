package com.example.imageauditservice.repository;

import com.example.imageauditservice.entities.RejectedResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RejectedResponseRepository extends JpaRepository<RejectedResponse, UUID> {
}
