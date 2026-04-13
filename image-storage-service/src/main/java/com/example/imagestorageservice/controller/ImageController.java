package com.example.imagestorageservice.controller;

import com.example.imagestorageservice.dto.PostImageResultDTO;
import com.example.imagestorageservice.exceptions.InvalidFileTypeException;
import com.example.imagestorageservice.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping("/images")
@AllArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Choose a file to upload.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image")) {
            throw new InvalidFileTypeException("Must provide a valid image type");
        }

        String uploadedFilename = imageService.uploadImage(contentType, file);

        if (!uploadedFilename.isEmpty()) {
            return ResponseEntity.ok(
                    new PostImageResultDTO("Image uploaded successfully", uploadedFilename, "/images/" + uploadedFilename));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image");
        }
    }

    @GetMapping("/{filename}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String filename) {
        InputStream inputStream = imageService.getImage(filename);

        if (inputStream == null) {
            return ResponseEntity.notFound().build();
        }

        String contentType;
        try {
            contentType = Files.probeContentType(Paths.get(filename));
        } catch (Exception e) {
            contentType = "application/octet-stream";
        }

        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(new InputStreamResource(inputStream));
    }
}