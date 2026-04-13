package com.example.imagestorageservice.config;

import io.minio.MinioClient;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class MinioConfig {
    @Value("${minio.user}")
    private String user;

    @Value("${minio.password}")
    private String password;

    @Value("${minio.endpoint}")
    private String endpoint;

    @Bean
    public MinioClient minioClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .callTimeout(35, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();

        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(user, password)
                .httpClient(httpClient)
                .build();
    }
}

