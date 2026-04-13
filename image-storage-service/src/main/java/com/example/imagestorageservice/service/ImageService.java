package com.example.imagestorageservice.service;

import io.minio.*;
import io.minio.errors.*;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.openmbean.InvalidKeyException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    public String uploadImage(String contentType, MultipartFile file) {
        String filename = UUID.randomUUID() + "." + file.getOriginalFilename();

        try (BufferedInputStream inputStream = new BufferedInputStream(file.getInputStream())) {
            ensureBucketExists();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .contentType(contentType)
                            .stream(inputStream, file.getSize(), -1l)
                            .build()
            );

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return filename;
    }

    public InputStream getImage(String filename) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .build()
            );
        } catch (Exception e) {
            System.err.println("Помилка при отриманні файлу з MinIO: " + e.getMessage());
            return null;
        }
    }

    public boolean deleteImage(String filename) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .build()
            );
            return true;
        } catch (Exception e) {
            System.err.println("Помилка при видаленні файлу з MinIO: " + e.getMessage());
            return false;
        }
    }

    private void ensureBucketExists() throws InvalidKeyException {
        boolean found = false;
        try {
            found = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
        } catch (MinioException e) {
            throw new RuntimeException(e);
        }
        if (!found) {
            try {
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(bucketName)
                                .build()
                );
            } catch (MinioException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
