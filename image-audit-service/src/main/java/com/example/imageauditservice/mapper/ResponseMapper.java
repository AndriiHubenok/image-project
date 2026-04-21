package com.example.imageauditservice.mapper;

import com.example.imageauditservice.dto.response.PostResponseDTO;
import com.example.imageauditservice.dto.response.PostResponseModerationDTO;
import com.example.imageauditservice.dto.response.UpdateModerationResultDTO;
import com.example.imageauditservice.entities.RejectedResponse;
import com.example.imageauditservice.entities.Response;
import org.springframework.stereotype.Component;

@Component
public class ResponseMapper {
    public PostResponseModerationDTO postResponseDTOToModeration(PostResponseDTO postResponseDTO) {
        PostResponseModerationDTO postResponseModerationDTO = new PostResponseModerationDTO();
        postResponseModerationDTO.setText(postResponseDTO.getText());
        return postResponseModerationDTO;
    }

    public Response toEntity(PostResponseDTO postResponseDTO) {
        Response response = new Response();
        response.setText(postResponseDTO.getText());
        return response;
    }

    public RejectedResponse toRejectedResponse(Response response, UpdateModerationResultDTO postResponseDTO) {
        RejectedResponse rejectedResponse = new RejectedResponse();
        rejectedResponse.setId(response.getId());
        rejectedResponse.setText(response.getText());
        rejectedResponse.setReason(postResponseDTO.getReason());
        return rejectedResponse;
    }

    public PostResponseModerationDTO responseToModeration(Response response) {
        PostResponseModerationDTO postResponseModerationDTO = new PostResponseModerationDTO();
        postResponseModerationDTO.setId(response.getId());
        postResponseModerationDTO.setText(response.getText());
        return postResponseModerationDTO;
    }
}
