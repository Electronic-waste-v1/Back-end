package org.example.ewastev0_1.mapper;


import org.example.ewastev0_1.domain.entites.Annonce;
import org.example.ewastev0_1.domain.entites.Ewaste;
import org.example.ewastev0_1.domain.entites.User;
import org.example.ewastev0_1.dto.request.AnnonceRequest;
import org.example.ewastev0_1.dto.response.AnnonceResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AnnonceMapper {

    @Mapping(target = "user", source = "user_id", qualifiedByName = "userIdToUser")
    @Mapping(target = "ewaste", source = "waste_id", qualifiedByName = "wasteIdToEwaste")
    Annonce toEntity(AnnonceRequest annonceRequest);

    AnnonceResponse toResponse(Annonce annonce);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "user", source = "user_id", qualifiedByName = "userIdToUser")
    @Mapping(target = "ewaste", source = "waste_id", qualifiedByName = "wasteIdToEwaste")
    void updateEntity(@MappingTarget Annonce annonce, AnnonceRequest annonceRequest);

    @Named("userIdToUser")
    default User userIdToUser(Integer userId) {
        if (userId == null) {
            return null;
        }
        User user = new User();
        user.setId(userId);
        return user;
    }

    @Named("wasteIdToEwaste")
    default Ewaste wasteIdToEwaste(Integer wasteId) {
        if (wasteId == null) {
            return null;
        }
        Ewaste ewaste = new Ewaste();
        ewaste.setId(wasteId);
        return ewaste;
    }
}