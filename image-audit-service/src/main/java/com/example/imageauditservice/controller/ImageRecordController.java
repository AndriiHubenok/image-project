package com.example.imageauditservice.controller;

import com.example.imageauditservice.config.exception.ImageRecordNotFound;
import com.example.imageauditservice.dto.GetImageRecordDTO;
import com.example.imageauditservice.service.ImageRecordService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/record")
@AllArgsConstructor
public class ImageRecordController {
    private ImageRecordService imageRecordService;

    @GetMapping
    public List<GetImageRecordDTO> getAllImageRecords() {
        return imageRecordService.getAllRecords();
    }

    @GetMapping("/{filename}")
    public List<GetImageRecordDTO> getImageRecordsByFilename(@PathVariable String filename) {
        return imageRecordService.getImageRecordsByFilename(filename);
    }

    @GetMapping("/id/{id}")
    public GetImageRecordDTO getImageRecordById(@PathVariable Long id) {
        GetImageRecordDTO dto = imageRecordService.getImageRecordById(id);
        if(dto == null) throw new ImageRecordNotFound("Image record not found");
        return dto;
    }

}
