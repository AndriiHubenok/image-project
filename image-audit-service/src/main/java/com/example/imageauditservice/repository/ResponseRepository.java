package com.example.imageauditservice.repository;

import com.example.imageauditservice.entities.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResponseRepository extends JpaRepository<Response, UUID> {
}
