package org.example.ewastev0_1.mapper;

import org.example.ewastev0_1.domain.entites.*;
import org.example.ewastev0_1.dto.request.*;
import org.example.ewastev0_1.dto.response.*;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = {})
public interface CommunityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "likesCount", ignore = true)
    @Mapping(target = "commentsCount", ignore = true)
    @Mapping(target = "likedBy", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(source = "author", target = "author")
    @Mapping(source = "request.title", target = "title")
    @Mapping(source = "request.content", target = "content")
    @Mapping(source = "request.tags", target = "tags")
    @Mapping(source = "request.imageUrl", target = "imageUrl")
    CommunityPost mapToEntity(CommunityPostRequest request, User author);

    @Mapping(source = "post.id", target = "id")
    @Mapping(source = "post.title", target = "title")
    @Mapping(source = "post.content", target = "content")
    @Mapping(source = "post.tags", target = "tags")
    @Mapping(source = "post.imageUrl", target = "imageUrl")
    @Mapping(source = "post.likesCount", target = "likesCount")
    @Mapping(source = "post.commentsCount", target = "commentsCount")
    @Mapping(source = "post.createdAt", target = "createdAt")
    @Mapping(source = "post.updatedAt", target = "updatedAt")
    @Mapping(source = "post.author", target = "author", qualifiedByName = "userToUserSummary")

    @Mapping(target = "isLikedByCurrentUser", expression = "java(post.getLikedBy() != null && post.getLikedBy().contains(currentUser))")
    CommunityPostResponse mapToResponse(CommunityPost post, User currentUser);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "likesCount", ignore = true)
    @Mapping(target = "likedBy", ignore = true)
    @Mapping(source = "author", target = "author")
    @Mapping(source = "post", target = "post")
    @Mapping(source = "request.content", target = "content")
    Comment mapToEntity(CommentRequest request, User author, CommunityPost post);

    @Mapping(source = "comment.id", target = "id")
    @Mapping(source = "comment.content", target = "content")
    @Mapping(source = "comment.likesCount", target = "likesCount")
    @Mapping(source = "comment.createdAt", target = "createdAt")
    @Mapping(source = "comment.updatedAt", target = "updatedAt")
    @Mapping(source = "comment.author", target = "author", qualifiedByName = "userToUserSummary")
    @Mapping(target = "isLikedByCurrentUser", expression = "java(comment.getLikedBy() != null && comment.getLikedBy().contains(currentUser))")
    CommentResponse mapToResponse(Comment comment, User currentUser);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "attendeesCount", ignore = true)
    @Mapping(target = "attendees", ignore = true)
    @Mapping(source = "organizer", target = "organizer")
    @Mapping(source = "request.title", target = "title")
    @Mapping(source = "request.description", target = "description")
    @Mapping(source = "request.startDate", target = "startDate")
    @Mapping(source = "request.endDate", target = "endDate")
    @Mapping(source = "request.location", target = "location")
    @Mapping(source = "request.imageUrl", target = "imageUrl")
    CommunityEvent mapToEntity(CommunityEventRequest request, User organizer);

    @Mapping(source = "event.id", target = "id")
    @Mapping(source = "event.title", target = "title")
    @Mapping(source = "event.description", target = "description")
    @Mapping(source = "event.startDate", target = "startDate")
    @Mapping(source = "event.endDate", target = "endDate")
    @Mapping(source = "event.location", target = "location")
    @Mapping(source = "event.imageUrl", target = "imageUrl")
    @Mapping(source = "event.attendeesCount", target = "attendeesCount")
    @Mapping(source = "event.createdAt", target = "createdAt")
    @Mapping(source = "event.updatedAt", target = "updatedAt")
    @Mapping(source = "event.organizer", target = "organizer", qualifiedByName = "userToUserSummary")
    @Mapping(target = "isAttendingByCurrentUser", expression = "java(event.getAttendees() != null && event.getAttendees().contains(currentUser))")
    CommunityEventResponse mapToResponse(CommunityEvent event, User currentUser);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "currentProgress", ignore = true)
    @Mapping(target = "participantsCount", ignore = true)
    @Mapping(target = "participants", ignore = true)
    @Mapping(source = "creator", target = "creator")
    @Mapping(source = "request.title", target = "title")
    @Mapping(source = "request.description", target = "description")
    @Mapping(source = "request.startDate", target = "startDate")
    @Mapping(source = "request.endDate", target = "endDate")
    @Mapping(source = "request.reward", target = "reward")
    @Mapping(source = "request.targetGoal", target = "targetGoal")
    @Mapping(source = "request.imageUrl", target = "imageUrl")
    CommunityChallenge mapToEntity(CommunityChallengeRequest request, User creator);

    @Mapping(source = "challenge.id", target = "id")
    @Mapping(source = "challenge.title", target = "title")
    @Mapping(source = "challenge.description", target = "description")
    @Mapping(source = "challenge.startDate", target = "startDate")
    @Mapping(source = "challenge.endDate", target = "endDate")
    @Mapping(source = "challenge.imageUrl", target = "imageUrl")
    @Mapping(source = "challenge.reward", target = "reward")
    @Mapping(source = "challenge.targetGoal", target = "targetGoal")
    @Mapping(source = "challenge.currentProgress", target = "currentProgress")
    @Mapping(source = "challenge.participantsCount", target = "participantsCount")
    @Mapping(source = "challenge.createdAt", target = "createdAt")
    @Mapping(source = "challenge.updatedAt", target = "updatedAt")
    @Mapping(source = "challenge.creator", target = "creator", qualifiedByName = "userToUserSummary")
    @Mapping(target = "isParticipatingByCurrentUser", expression = "java(challenge.getParticipants() != null && challenge.getParticipants().contains(currentUser))")
    CommunityChallengeResponse mapToResponse(CommunityChallenge challenge, User currentUser);
    @Named("userToUserSummary")
    default UserSummaryResponse mapToUserSummary(User user) {
        if (user == null) {
            return null;
        }

        return UserSummaryResponse.builder()
                .id(user.getId())
                .username(user.getUsername())

                .badge(determineBadge(user))
                .build();
    }

    default String determineBadge(User user) {

        int points = user.getUserPoints().getPointsTotal() != null ? user.getUserPoints().getPointsTotal() : 0;

        if (points >= 5000) {
            return "Platinum";
        } else if (points >= 3000) {
            return "Gold";
        } else if (points >= 1000) {
            return "Silver";
        } else {
            return "Bronze";
        }
    }

    default <T> PagedResponse<T> mapToPagedResponse(Page<T> page) {
        if (page == null) {
            return null;
        }

        return PagedResponse.<T>builder()
                .content(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}

