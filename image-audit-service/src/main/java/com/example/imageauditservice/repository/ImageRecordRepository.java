package com.example.imageauditservice.repository;

import com.example.imageauditservice.entities.ImageRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRecordRepository extends JpaRepository<ImageRecord, Long> {
    Optional<ImageRecord> findByFilename(String filename);
}
