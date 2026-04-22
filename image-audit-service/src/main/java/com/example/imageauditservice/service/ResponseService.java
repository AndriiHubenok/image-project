package com.example.imageauditservice.service;

import com.example.imageauditservice.amqp.MessageSender;
import com.example.imageauditservice.dto.enums.Status;
import com.example.imageauditservice.dto.response.GetRejectedResponseDTO;
import com.example.imageauditservice.dto.response.GetResponseDTO;
import com.example.imageauditservice.dto.response.PostResponseDTO;
import com.example.imageauditservice.dto.response.UpdateModerationResultDTO;
import com.example.imageauditservice.entities.RejectedResponse;
import com.example.imageauditservice.entities.Response;
import com.example.imageauditservice.mapper.ResponseMapper;
import com.example.imageauditservice.repository.RejectedResponseRepository;
import com.example.imageauditservice.repository.ResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final ResponseRepository responseRepository;
    private final MessageSender messageSender;
    private final ResponseMapper responseMapper;
    private final RejectedResponseRepository rejectedResponseRepository;

    public void postResponse(PostResponseDTO postResponseDTO) {
        Response response = responseMapper.toEntity(postResponseDTO);
        response.setStatus(Status.PENDING);
        response = responseRepository.save(response);
        messageSender.send(responseMapper.responseToModeration(response));

    }

    public List<GetResponseDTO> getAllResponses() {
        List<Response> responses = responseRepository.findAll();
        return responses.stream().map(responseMapper::toGetResponseDTO).toList();
    }

    public List<GetRejectedResponseDTO> getAllRejectedResponses() {
        List<RejectedResponse> rejectedResponses = rejectedResponseRepository.findAll();
        return rejectedResponses.stream().map(responseMapper::toGetRejectedResponseDTO).toList();
    }

    public Response updateResponse(UpdateModerationResultDTO updatedResult){
        Response response = responseRepository.findById(updatedResult.getId()).orElseThrow(() -> new RuntimeException("Response not found"));
        if(response.getStatus() != Status.PENDING) return null;

        if(!updatedResult.isSafe()) {
            RejectedResponse rejectedResponse = responseMapper.toRejectedResponse(response, updatedResult);
            rejectedResponseRepository.save(rejectedResponse);
            response.setStatus(Status.REJECTED);
        } else {
            response.setStatus(Status.PUBLISHED);
        }
        return responseRepository.save(response);
    }
}
