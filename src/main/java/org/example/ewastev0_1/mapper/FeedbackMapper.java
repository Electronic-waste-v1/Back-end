package org.example.ewastev0_1.mapper;


import org.example.ewastev0_1.domain.entites.Annonce;
import org.example.ewastev0_1.domain.entites.Feedback;
import org.example.ewastev0_1.dto.request.AnnonceRequest;
import org.example.ewastev0_1.dto.request.FeedbackRequest;
import org.example.ewastev0_1.dto.response.FeedbackResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    Feedback toEntity(FeedbackRequest feedbackRequest);
    FeedbackResponse toResponse(Feedback feedback);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Feedback feedback, FeedbackRequest feedbackRequest);

}
