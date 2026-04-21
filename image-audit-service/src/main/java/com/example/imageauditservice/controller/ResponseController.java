package com.example.imageauditservice.controller;

import com.example.imageauditservice.dto.response.PostResponseDTO;
import com.example.imageauditservice.service.ResponseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/response")
@RequiredArgsConstructor
public class ResponseController {
    private final ResponseService responseService;

    @PostMapping
    public ResponseEntity<String> postResponse(@RequestBody @Valid PostResponseDTO postResponseDTO){
        responseService.postResponse(postResponseDTO);
        return ResponseEntity.ok("Response posted successfully");
    }
}
