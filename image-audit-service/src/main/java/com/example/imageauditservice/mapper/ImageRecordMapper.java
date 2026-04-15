package com.example.imageauditservice.mapper;

import com.example.imageauditservice.dto.GetImageRecordDTO;
import com.example.imageauditservice.entities.ImageRecord;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ImageRecordMapper {
    public GetImageRecordDTO imageRecordToGetImageRecordDTO(ImageRecord imageRecord) {
        if (imageRecord == null) return null;
        GetImageRecordDTO dto = new GetImageRecordDTO();
        dto.setId(imageRecord.getId());
        dto.setFilename(imageRecord.getFilename());
        dto.setAction(imageRecord.getAction());
        dto.setCreatedAt(imageRecord.getCreatedAt());
        return dto;
    }

    public List<GetImageRecordDTO> imageRecordListToGetImageRecordDTOList(List<ImageRecord> imageRecords) {
        return imageRecords.stream()
                .map(this::imageRecordToGetImageRecordDTO)
                .toList();
    }
}
