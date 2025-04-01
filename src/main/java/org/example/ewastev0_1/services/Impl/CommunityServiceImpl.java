package org.example.ewastev0_1.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.example.ewastev0_1.domain.entites.*;
import org.example.ewastev0_1.dto.request.*;
import org.example.ewastev0_1.dto.response.*;
import org.example.ewastev0_1.exception.ResourceNotFoundException;
import org.example.ewastev0_1.exception.UnauthorizedException;
import org.example.ewastev0_1.mapper.CommunityMapper;
import org.example.ewastev0_1.repository.*;
import org.example.ewastev0_1.services.Interface.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Transactional
public class CommunityServiceImpl implements CommunityService {
    
    private final CommunityPostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommunityEventRepository eventRepository;
    private final CommunityChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final CommunityMapper mapper;

    @Override
    public PagedResponse<CommunityPostResponse> getAllPosts(Pageable pageable, Integer currentUserId) {
        log.info("Fetching all posts with pagination");
        User currentUser = getUserById(currentUserId);

        Page<CommunityPost> posts = postRepository.findAll(pageable);
        Page<CommunityPostResponse> postResponses = posts.map(post -> mapper.mapToResponse(post, currentUser));

        return mapper.mapToPagedResponse(postResponses);
    }

//    @Override
//    public PagedResponse<CommunityPostResponse> searchPosts(String keyword, Pageable pageable, Integer currentUserId) {
//        log.info("Searching posts with keyword: {}", keyword);
//        User currentUser = getUserById(currentUserId);
//
//        Page<CommunityPost> posts = postRepository.searchPosts(keyword, pageable);
//        Page<CommunityPostResponse> postResponses = posts.map(post -> mapper.mapToResponse(post, currentUser));
//
//        return mapper.mapToPagedResponse(postResponses);
//    }
//
    @Override
    public CommunityPostResponse getPostById(Integer postId, Integer currentUserId) {
        log.info("Fetching post with ID: {}", postId);
        User currentUser = getUserById(currentUserId);
        CommunityPost post = getPostEntityById(postId);
        
        return mapper.mapToResponse(post, currentUser);
    }
    
    @Override
    public CommunityPostResponse createPost(CommunityPostRequest request, Integer authorId) {
        log.info("Creating new post by user ID: {}", authorId);
        User author = getUserById(authorId);
        
        CommunityPost post = mapper.mapToEntity(request, author);
        CommunityPost savedPost = postRepository.save(post);
        
        log.info("Post created with ID: {}", savedPost.getId());
        return mapper.mapToResponse(savedPost, author);
    }
    
    @Override
    public CommunityPostResponse updatePost(Integer postId, CommunityPostRequest request, Integer currentUserId) {
        log.info("Updating post with ID: {} by user ID: {}", postId, currentUserId);
        User currentUser = getUserById(currentUserId);
        CommunityPost post = getPostEntityById(postId);

        if (!post.getAuthor().getId().equals(currentUserId)) {
            log.error("User ID: {} is not authorized to update post ID: {}", currentUserId, postId);
            throw new UnauthorizedException("You are not authorized to update this post");
        }
        
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setTags(request.getTags());
        post.setImageUrl(request.getImageUrl());
        
        CommunityPost updatedPost = postRepository.save(post);
        log.info("Post updated with ID: {}", updatedPost.getId());
        
        return mapper.mapToResponse(updatedPost, currentUser);
    }
    
    @Override
    public void deletePost(Integer postId, Integer currentUserId) {
        log.info("Deleting post with ID: {} by user ID: {}", postId, currentUserId);
        CommunityPost post = getPostEntityById(postId);

        if (!post.getAuthor().getId().equals(currentUserId)) {
            log.error("User ID: {} is not authorized to delete post ID: {}", currentUserId, postId);
            throw new UnauthorizedException("You are not authorized to delete this post");
        }
        
        postRepository.delete(post);
        log.info("Post deleted with ID: {}", postId);
    }

    @Override
    public CommunityPostResponse likePost(Integer postId, Integer userId) {
        log.info("User ID: {} liking post ID: {}", userId, postId);


        User user = getUserById(userId);
        CommunityPost post = getPostEntityById(postId);


        if (post.getLikesCount() == null) {
            post.setLikesCount(0);
        }

        if (!post.getLikedBy().contains(user)) {
            post.getLikedBy().add(user);
            post.setLikesCount(post.getLikesCount() + 1);
            CommunityPost savedPost = postRepository.save(post);

            log.info("Post {} liked by user {}. Total likes: {}",
                    savedPost.getId(), user.getId(), savedPost.getLikesCount());
        }

        return mapper.mapToResponse(post, user);
    }
    @Override
    public CommunityPostResponse unlikePost(Integer postId, Integer userId) {
        log.info("User ID: {} unliking post ID: {}", userId, postId);
        User user = getUserById(userId);
        CommunityPost post = getPostEntityById(postId);
        
        if (post.getLikedBy().contains(user)) {
            post.getLikedBy().remove(user);
            post.setLikesCount(post.getLikesCount() - 1);
            postRepository.save(post);
            log.info("Post unliked successfully");
        }
        
        return mapper.mapToResponse(post, user);
    }
    
    @Override
    public PagedResponse<CommunityPostResponse> searchPosts(String keyword, Pageable pageable, Integer currentUserId) {
        log.info("Searching posts with keyword: {}", keyword);
        User currentUser = getUserById(currentUserId);

        Page<CommunityPost> posts = postRepository.searchPosts(keyword, pageable);
        Page<CommunityPostResponse> postResponses = posts.map(post -> mapper.mapToResponse(post, currentUser));

        return mapper.mapToPagedResponse(postResponses);
    }
    
    @Override
    public PagedResponse<CommunityPostResponse> getPostsByTag(String tag, Pageable pageable, Integer currentUserId) {
        log.info("Fetching posts with tag: {}", tag);
        User currentUser = getUserById(currentUserId);
        
        Page<CommunityPost> posts = postRepository.findByTag(tag, pageable);
        Page<CommunityPostResponse> postResponses = posts.map(post -> mapper.mapToResponse(post, currentUser));
        
        return mapper.mapToPagedResponse(postResponses);
    }
    
    @Override
    public PagedResponse<CommunityPostResponse> getMostLikedPosts(Pageable pageable, Integer currentUserId) {
        log.info("Fetching most liked posts");
        User currentUser = getUserById(currentUserId);
        
        Page<CommunityPost> posts = postRepository.findMostLikedPosts(pageable);
        Page<CommunityPostResponse> postResponses = posts.map(post -> mapper.mapToResponse(post, currentUser));
        
        return mapper.mapToPagedResponse(postResponses);
    }
    
    @Override
    public PagedResponse<CommunityPostResponse> getMostCommentedPosts(Pageable pageable, Integer currentUserId) {
        log.info("Fetching most commented posts");
        User currentUser = getUserById(currentUserId);
        
        Page<CommunityPost> posts = postRepository.findMostCommentedPosts(pageable);
        Page<CommunityPostResponse> postResponses = posts.map(post -> mapper.mapToResponse(post, currentUser));
        
        return mapper.mapToPagedResponse(postResponses);
    }
    
    // Comment operations
    @Override
    public PagedResponse<CommentResponse> getCommentsByPostId(Integer postId, Pageable pageable, Integer currentUserId) {
        log.info("Fetching comments for post ID: {}", postId);
        User currentUser = getUserById(currentUserId);
        CommunityPost post = getPostEntityById(postId);
        
        Page<Comment> comments = commentRepository.findByPostOrderByCreatedAtDesc(post, pageable);
        Page<CommentResponse> commentResponses = comments.map(comment -> mapper.mapToResponse(comment, currentUser));
        
        return mapper.mapToPagedResponse(commentResponses);
    }

    @Override
    public CommentResponse addComment(Integer postId, CommentRequest request, Integer authorId) {
        log.info("Adding comment to post ID: {} by user ID: {}", postId, authorId);
        User author = getUserById(authorId);
        CommunityPost post = getPostEntityById(postId);

        Comment comment = mapper.mapToEntity(request, author, post);
        Comment savedComment = commentRepository.save(comment);

        // Initialize commentsCount if null
        if (post.getCommentsCount() == null) {
            post.setCommentsCount(0);
        }
        post.setCommentsCount(post.getCommentsCount() + 1);
        postRepository.save(post);

        log.info("Comment added with ID: {}", savedComment.getId());
        return mapper.mapToResponse(savedComment, author);
    }
    
    @Override
    public CommentResponse updateComment(Integer commentId, CommentRequest request, Integer currentUserId) {
        log.info("Updating comment with ID: {} by user ID: {}", commentId, currentUserId);
        User currentUser = getUserById(currentUserId);
        Comment comment = getCommentEntityById(commentId);
        
        // Check if the current user is the author
        if (!comment.getAuthor().getId().equals(currentUserId)) {
            log.error("User ID: {} is not authorized to update comment ID: {}", currentUserId, commentId);
            throw new UnauthorizedException("You are not authorized to update this comment");
        }
        
        comment.setContent(request.getContent());
        Comment updatedComment = commentRepository.save(comment);
        
        log.info("Comment updated with ID: {}", updatedComment.getId());
        return mapper.mapToResponse(updatedComment, currentUser);
    }
    
    @Override
    public void deleteComment(Integer commentId, Integer currentUserId) {
        log.info("Deleting comment with ID: {} by user ID: {}", commentId, currentUserId);
        Comment comment = getCommentEntityById(commentId);
        
        // Check if the current user is the author
        if (!comment.getAuthor().getId().equals(currentUserId)) {
            log.error("User ID: {} is not authorized to delete comment ID: {}", currentUserId, commentId);
            throw new UnauthorizedException("You are not authorized to delete this comment");
        }
        
        // Update comment count on post
        CommunityPost post = comment.getPost();
        post.setCommentsCount(post.getCommentsCount() - 1);
        postRepository.save(post);
        
        commentRepository.delete(comment);
        log.info("Comment deleted with ID: {}", commentId);
    }
    
    @Override
    public CommentResponse likeComment(Integer commentId, Integer userId) {
        log.info("User ID: {} liking comment ID: {}", userId, commentId);
        User user = getUserById(userId);
        Comment comment = getCommentEntityById(commentId);
        log.info("dslkjsdk");
        if (!comment.getLikedBy().contains(user)) {
            comment.getLikedBy().add(user);
            comment.setLikesCount(comment.getLikesCount() + 1);
            commentRepository.save(comment);
            log.info("Comment liked successfully");
        }
        
        return mapper.mapToResponse(comment, user);
    }
    
    @Override
    public CommentResponse unlikeComment(Integer commentId, Integer userId) {
        log.info("User ID: {} unliking comment ID: {}", userId, commentId);
        User user = getUserById(userId);
        Comment comment = getCommentEntityById(commentId);
        
        if (comment.getLikedBy().contains(user)) {
            comment.getLikedBy().remove(user);
            comment.setLikesCount(comment.getLikesCount() - 1);
            commentRepository.save(comment);
            log.info("Comment unliked successfully");
        }
        
        return mapper.mapToResponse(comment, user);
    }
    
    // Event operations
    @Override
    public PagedResponse<CommunityEventResponse> getUpcomingEvents(Pageable pageable, Integer currentUserId) {
        log.info("Fetching upcoming events");
        User currentUser = getUserById(currentUserId);
        
        Page<CommunityEvent> events = eventRepository.findUpcomingEvents(LocalDateTime.now(), pageable);
        Page<CommunityEventResponse> eventResponses = events.map(event -> mapper.mapToResponse(event, currentUser));
        
        return mapper.mapToPagedResponse(eventResponses);
    }
    
    @Override
    public PagedResponse<CommunityEventResponse> getOngoingEvents(Pageable pageable, Integer currentUserId) {
        log.info("Fetching ongoing events");
        User currentUser = getUserById(currentUserId);
        
        Page<CommunityEvent> events = eventRepository.findOngoingEvents(LocalDateTime.now(), pageable);
        Page<CommunityEventResponse> eventResponses = events.map(event -> mapper.mapToResponse(event, currentUser));
        
        return mapper.mapToPagedResponse(eventResponses);
    }
    
    @Override
    public PagedResponse<CommunityEventResponse> getPastEvents(Pageable pageable, Integer currentUserId) {
        log.info("Fetching past events");
        User currentUser = getUserById(currentUserId);
        
        Page<CommunityEvent> events = eventRepository.findPastEvents(LocalDateTime.now(), pageable);
        Page<CommunityEventResponse> eventResponses = events.map(event -> mapper.mapToResponse(event, currentUser));
        
        return mapper.mapToPagedResponse(eventResponses);
    }
    
    @Override
    public CommunityEventResponse getEventById(Integer eventId, Integer currentUserId) {
        log.info("Fetching event with ID: {}", eventId);
        User currentUser = getUserById(currentUserId);
        CommunityEvent event = getEventEntityById(eventId);
        
        return mapper.mapToResponse(event, currentUser);
    }
    
    @Override
    public CommunityEventResponse createEvent(CommunityEventRequest request, Integer organizerId) {
        log.info("Creating new event by user ID: {}", organizerId);
        User organizer = getUserById(organizerId);
        
        CommunityEvent event = mapper.mapToEntity(request, organizer);
        CommunityEvent savedEvent = eventRepository.save(event);
        
        log.info("Event created with ID: {}", savedEvent.getId());
        return mapper.mapToResponse(savedEvent, organizer);
    }
    
    @Override
    public CommunityEventResponse updateEvent(Integer eventId, CommunityEventRequest request, Integer currentUserId) {
        log.info("Updating event with ID: {} by user ID: {}", eventId, currentUserId);
        User currentUser = getUserById(currentUserId);
        CommunityEvent event = getEventEntityById(eventId);
        
        // Check if the current user is the organizer
        if (!event.getOrganizer().getId().equals(currentUserId)) {
            log.error("User ID: {} is not authorized to update event ID: {}", currentUserId, eventId);
            throw new UnauthorizedException("You are not authorized to update this event");
        }
        
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());
        event.setLocation(request.getLocation());
        event.setImageUrl(request.getImageUrl());
        
        CommunityEvent updatedEvent = eventRepository.save(event);
        log.info("Event updated with ID: {}", updatedEvent.getId());
        
        return mapper.mapToResponse(updatedEvent, currentUser);
    }
    
    @Override
    public void deleteEvent(Integer eventId, Integer currentUserId) {
        log.info("Deleting event with ID: {} by user ID: {}", eventId, currentUserId);
        CommunityEvent event = getEventEntityById(eventId);
        
        // Check if the current user is the organizer
        if (!event.getOrganizer().getId().equals(currentUserId)) {
            log.error("User ID: {} is not authorized to delete event ID: {}", currentUserId, eventId);
            throw new UnauthorizedException("You are not authorized to delete this event");
        }
        
        eventRepository.delete(event);
        log.info("Event deleted with ID: {}", eventId);
    }

    @Override
    public CommunityEventResponse attendEvent(Integer eventId, Integer userId) {
        log.info("User ID: {} attending event ID: {}", userId, eventId);
        User user = getUserById(userId);
        CommunityEvent event = getEventEntityById(eventId);
        log.info("event: {}", event);

        if (!event.getAttendees().contains(user)) {
            event.getAttendees().add(user);


            Integer currentCount = event.getAttendeesCount();
            if (currentCount == null) {
                event.setAttendeesCount(1);
            } else {
                event.setAttendeesCount(currentCount + 1);
            }

            eventRepository.save(event);
            log.info("User is now attending the event");
        }

        return mapper.mapToResponse(event, user);
    }
    @Override
    public CommunityEventResponse unattendEvent(Integer eventId, Integer userId) {
        log.info("User ID: {} unattending event ID: {}", userId, eventId);
        User user = getUserById(userId);
        CommunityEvent event = getEventEntityById(eventId);
        
        if (event.getAttendees().contains(user)) {
            event.getAttendees().remove(user);
            event.setAttendeesCount(event.getAttendeesCount() - 1);
            eventRepository.save(event);
            log.info("User is no longer attending the event");
        }
        
        return mapper.mapToResponse(event, user);
    }
    

    @Override
    public PagedResponse<CommunityChallengeResponse> getActiveChallenges(Pageable pageable, Integer currentUserId) {
        log.info("Fetching active challenges");
        User currentUser = getUserById(currentUserId);
        
        Page<CommunityChallenge> challenges = challengeRepository.findActiveChallenges(LocalDateTime.now(), pageable);
        Page<CommunityChallengeResponse> challengeResponses = challenges.map(challenge -> mapper.mapToResponse(challenge, currentUser));
        
        return mapper.mapToPagedResponse(challengeResponses);
    }
    
    @Override
    public PagedResponse<CommunityChallengeResponse> getUpcomingChallenges(Pageable pageable, Integer currentUserId) {
        log.info("Fetching upcoming challenges");
        User currentUser = getUserById(currentUserId);
        
        Page<CommunityChallenge> challenges = challengeRepository.findUpcomingChallenges(LocalDateTime.now(), pageable);
        Page<CommunityChallengeResponse> challengeResponses = challenges.map(challenge -> mapper.mapToResponse(challenge, currentUser));
        
        return mapper.mapToPagedResponse(challengeResponses);
    }
    
    @Override
    public PagedResponse<CommunityChallengeResponse> getCompletedChallenges(Pageable pageable, Integer currentUserId) {
        log.info("Fetching completed challenges");
        User currentUser = getUserById(currentUserId);
        
        Page<CommunityChallenge> challenges = challengeRepository.findCompletedChallenges(LocalDateTime.now(), pageable);
        Page<CommunityChallengeResponse> challengeResponses = challenges.map(challenge -> mapper.mapToResponse(challenge, currentUser));
        
        return mapper.mapToPagedResponse(challengeResponses);
    }
    
    @Override
    public CommunityChallengeResponse getChallengeById(Integer challengeId, Integer currentUserId) {
        log.info("Fetching challenge with ID: {}", challengeId);
        User currentUser = getUserById(currentUserId);
        CommunityChallenge challenge = getChallengeEntityById(challengeId);
        
        return mapper.mapToResponse(challenge, currentUser);
    }
    
    @Override
    public CommunityChallengeResponse createChallenge(CommunityChallengeRequest request, Integer creatorId) {
        log.info("Creating new challenge by user ID: {}", creatorId);
        User creator = getUserById(creatorId);
        
        CommunityChallenge challenge = mapper.mapToEntity(request, creator);
        CommunityChallenge savedChallenge = challengeRepository.save(challenge);
        
        log.info("Challenge created with ID: {}", savedChallenge.getId());
        return mapper.mapToResponse(savedChallenge, creator);
    }
    
    @Override
    public CommunityChallengeResponse updateChallenge(Integer challengeId, CommunityChallengeRequest request, Integer currentUserId) {
        log.info("Updating challenge with ID: {} by user ID: {}", challengeId, currentUserId);
        User currentUser = getUserById(currentUserId);
        CommunityChallenge challenge = getChallengeEntityById(challengeId);
        

        if (!challenge.getCreator().getId().equals(currentUserId)) {
            log.error("User ID: {} is not authorized to update challenge ID: {}", currentUserId, challengeId);
            throw new UnauthorizedException("You are not authorized to update this challenge");
        }
        
        challenge.setTitle(request.getTitle());
        challenge.setDescription(request.getDescription());
        challenge.setStartDate(request.getStartDate());
        challenge.setEndDate(request.getEndDate());
        challenge.setReward(request.getReward());
        challenge.setTargetGoal(request.getTargetGoal());
        challenge.setImageUrl(request.getImageUrl());
        
        CommunityChallenge updatedChallenge = challengeRepository.save(challenge);
        log.info("Challenge updated with ID: {}", updatedChallenge.getId());
        
        return mapper.mapToResponse(updatedChallenge, currentUser);
    }
    
    @Override
    public void deleteChallenge(Integer challengeId, Integer currentUserId) {
        log.info("Deleting challenge with ID: {} by user ID: {}", challengeId, currentUserId);
        CommunityChallenge challenge = getChallengeEntityById(challengeId);
        

        if (!challenge.getCreator().getId().equals(currentUserId)) {
            log.error("User ID: {} is not authorized to delete challenge ID: {}", currentUserId, challengeId);
            throw new UnauthorizedException("You are not authorized to delete this challenge");
        }
        
        challengeRepository.delete(challenge);
        log.info("Challenge deleted with ID: {}", challengeId);
    }

    @Override
    public CommunityChallengeResponse joinChallenge(Integer challengeId, Integer userId) {
        log.info("User ID: {} joining challenge ID: {}", userId, challengeId);

        User user = getUserById(userId);
        CommunityChallenge challenge = getChallengeEntityById(challengeId);


        log.debug("User {} joining challenge {}", user.getId(), challenge.getId());

        // Initialize counts if null (defensive programming)
        if (challenge.getParticipantsCount() == null) {
            challenge.setParticipantsCount(0);
        }

        if (!challenge.getParticipants().contains(user)) {
            challenge.getParticipants().add(user);
            challenge.setParticipantsCount(challenge.getParticipantsCount() + 1);
            CommunityChallenge savedChallenge = challengeRepository.save(challenge);

            log.info("User {} joined challenge {}. Total participants: {}",
                    user.getId(), challenge.getId(), savedChallenge.getParticipantsCount());
        }

        return mapper.mapToResponse(challenge, user);
    }
    
    @Override
    public CommunityChallengeResponse leaveChallenge(Integer challengeId, Integer userId) {
        log.info("User ID: {} leaving challenge ID: {}", userId, challengeId);
        User user = getUserById(userId);
        CommunityChallenge challenge = getChallengeEntityById(challengeId);
        
        if (challenge.getParticipants().contains(user)) {
            challenge.getParticipants().remove(user);
            challenge.setParticipantsCount(challenge.getParticipantsCount() - 1);
            challengeRepository.save(challenge);
            log.info("User left the challenge successfully");
        }
        
        return mapper.mapToResponse(challenge, user);
    }
    
    @Override
    public CommunityChallengeResponse updateChallengeProgress(Integer challengeId, Integer progress) {
        log.info("Updating progress for challenge ID: {} to {}", challengeId, progress);
        CommunityChallenge challenge = getChallengeEntityById(challengeId);
        
        challenge.setCurrentProgress(progress);
        CommunityChallenge updatedChallenge = challengeRepository.save(challenge);
        
        log.info("Challenge progress updated successfully");
        return mapper.mapToResponse(updatedChallenge, null);
    }
    

    private User getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
    }
    
    private CommunityPost getPostEntityById(Integer postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + postId));
    }
    
    private Comment getCommentEntityById(Integer commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with ID: " + commentId));
    }
    
    private CommunityEvent getEventEntityById(Integer eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));
    }
    
    private CommunityChallenge getChallengeEntityById(Integer challengeId) {
        return challengeRepository.findById(challengeId)
                .orElseThrow(() -> new ResourceNotFoundException("Challenge not found with ID: " + challengeId));
    }
}

