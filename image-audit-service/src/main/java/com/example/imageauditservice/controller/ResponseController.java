package com.example.imageauditservice.controller;

import com.example.imageauditservice.dto.response.GetRejectedResponseDTO;
import com.example.imageauditservice.dto.response.GetResponseDTO;
import com.example.imageauditservice.dto.response.PostResponseDTO;
import com.example.imageauditservice.service.ResponseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<List<GetResponseDTO>> getAllResponses(){
        return ResponseEntity.ok(responseService.getAllResponses());
    }

    @GetMapping("/rejected")
    public ResponseEntity<List<GetRejectedResponseDTO>> getRejectedResponses(){
        return ResponseEntity.ok(responseService.getAllRejectedResponses());
    }
}
