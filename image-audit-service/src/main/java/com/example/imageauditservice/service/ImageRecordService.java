package com.example.imageauditservice.service;

import com.example.imageauditservice.dto.Action;
import com.example.imageauditservice.entities.ImageRecord;
import com.example.imageauditservice.repository.ImageRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageRecordService {
    private final ImageRecordRepository imageRecordRepository;

    public ImageRecord save(String filename){
        ImageRecord imageRecord = new ImageRecord(filename, Action.UPLOADED);
        return imageRecordRepository.save(imageRecord);
    }

    public ImageRecord deleteByFilename(String filename){
        Optional<ImageRecord> record = imageRecordRepository.findByFilename(filename);
        if(record.isPresent()){
            ImageRecord imageRecordDelete = new ImageRecord(filename, Action.DELETED);
            return imageRecordRepository.save(imageRecordDelete);
        }
        return null;
    }
}
