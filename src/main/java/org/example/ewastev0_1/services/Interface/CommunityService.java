package org.example.ewastev0_1.services.Interface;

import org.example.ewastev0_1.dto.request.*;
import org.example.ewastev0_1.dto.response.*;
import org.springframework.data.domain.Pageable;

public interface CommunityService {
    

    PagedResponse<CommunityPostResponse> getAllPosts(Pageable pageable, Integer currentUserId);
    
    CommunityPostResponse getPostById(Integer postId, Integer currentUserId);
    
    CommunityPostResponse createPost(CommunityPostRequest request, Integer authorId);
    
    CommunityPostResponse updatePost(Integer postId, CommunityPostRequest request, Integer currentUserId);
    
    void deletePost(Integer postId, Integer currentUserId);
    
    CommunityPostResponse likePost(Integer postId, Integer userId);
    
    CommunityPostResponse unlikePost(Integer postId, Integer userId);
    
    PagedResponse<CommunityPostResponse> searchPosts(String keyword, Pageable pageable, Integer currentUserId);
    
    PagedResponse<CommunityPostResponse> getPostsByTag(String tag, Pageable pageable, Integer currentUserId);
    
    PagedResponse<CommunityPostResponse> getMostLikedPosts(Pageable pageable, Integer currentUserId);
    
    PagedResponse<CommunityPostResponse> getMostCommentedPosts(Pageable pageable, Integer currentUserId);
    

    PagedResponse<CommentResponse> getCommentsByPostId(Integer postId, Pageable pageable, Integer currentUserId);
    
    CommentResponse addComment(Integer postId, CommentRequest request, Integer authorId);
    
    CommentResponse updateComment(Integer commentId, CommentRequest request, Integer currentUserId);
    
    void deleteComment(Integer commentId, Integer currentUserId);
    
    CommentResponse likeComment(Integer commentId, Integer userId);
    
    CommentResponse unlikeComment(Integer commentId, Integer userId);
    

    PagedResponse<CommunityEventResponse> getUpcomingEvents(Pageable pageable, Integer currentUserId);
    
    PagedResponse<CommunityEventResponse> getOngoingEvents(Pageable pageable, Integer currentUserId);
    
    PagedResponse<CommunityEventResponse> getPastEvents(Pageable pageable, Integer currentUserId);
    
    CommunityEventResponse getEventById(Integer eventId, Integer currentUserId);
    
    CommunityEventResponse createEvent(CommunityEventRequest request, Integer organizerId);
    
    CommunityEventResponse updateEvent(Integer eventId, CommunityEventRequest request, Integer currentUserId);
    
    void deleteEvent(Integer eventId, Integer currentUserId);
    
    CommunityEventResponse attendEvent(Integer eventId, Integer userId);
    
    CommunityEventResponse unattendEvent(Integer eventId, Integer userId);
    

    PagedResponse<CommunityChallengeResponse> getActiveChallenges(Pageable pageable, Integer currentUserId);
    
    PagedResponse<CommunityChallengeResponse> getUpcomingChallenges(Pageable pageable, Integer currentUserId);
    
    PagedResponse<CommunityChallengeResponse> getCompletedChallenges(Pageable pageable, Integer currentUserId);
    
    CommunityChallengeResponse getChallengeById(Integer challengeId, Integer currentUserId);
    
    CommunityChallengeResponse createChallenge(CommunityChallengeRequest request, Integer creatorId);
    
    CommunityChallengeResponse updateChallenge(Integer challengeId, CommunityChallengeRequest request, Integer currentUserId);
    
    void deleteChallenge(Integer challengeId, Integer currentUserId);
    
    CommunityChallengeResponse joinChallenge(Integer challengeId, Integer userId);
    
    CommunityChallengeResponse leaveChallenge(Integer challengeId, Integer userId);
    
    CommunityChallengeResponse updateChallengeProgress(Integer challengeId, Integer progress);
}

