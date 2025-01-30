package org.example.ewastev0_1.mapper;


import org.example.ewastev0_1.domain.entites.Feedback;
import org.example.ewastev0_1.dto.request.FeedbackRequest;
import org.example.ewastev0_1.dto.response.FeedbackResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    Feedback toEntity(FeedbackRequest feedbackRequest);
    FeedbackResponse toResponse(Feedback feedback);
}
