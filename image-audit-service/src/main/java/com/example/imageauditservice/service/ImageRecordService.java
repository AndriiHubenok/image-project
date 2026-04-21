package com.example.imageauditservice.service;

import com.example.imageauditservice.dto.enums.Action;
import com.example.imageauditservice.dto.image.GetImageRecordDTO;
import com.example.imageauditservice.entities.ImageRecord;
import com.example.imageauditservice.mapper.ImageRecordMapper;
import com.example.imageauditservice.repository.ImageRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageRecordService {
    private final ImageRecordRepository imageRecordRepository;
    private final ImageRecordMapper imageRecordMapper;

    public ImageRecord save(String filename){
        ImageRecord imageRecord = new ImageRecord(filename, Action.UPLOADED);
        return imageRecordRepository.save(imageRecord);
    }

    public List<GetImageRecordDTO> getAllRecords(){
        return imageRecordMapper.imageRecordListToGetImageRecordDTOList(imageRecordRepository.findAll());
    }

    public List<GetImageRecordDTO> getImageRecordsByFilename(String filename){
        return imageRecordMapper.imageRecordListToGetImageRecordDTOList(imageRecordRepository.findAllByFilename(filename));
    }

    public GetImageRecordDTO getImageRecordById(Long id){
        return imageRecordMapper.imageRecordToGetImageRecordDTO(imageRecordRepository.findById(id).orElse(null));
    }

    public ImageRecord deleteByFilename(String filename){
        Optional<ImageRecord> imageRecord = imageRecordRepository.findByFilename(filename);
        if(imageRecord.isPresent()){
            ImageRecord imageRecordDelete = new ImageRecord(filename, Action.DELETED);
            return imageRecordRepository.save(imageRecordDelete);
        }
        return null;
    }
}
