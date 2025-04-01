package org.example.ewastev0_1.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ewastev0_1.dto.request.*;
import org.example.ewastev0_1.dto.response.*;
import org.example.ewastev0_1.services.Interface.CommunityService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class CommunityController {
    
    private final CommunityService communityService;
    
    // Post endpoints
    @GetMapping("/posts")
    public ResponseEntity<PagedResponse<CommunityPostResponse>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Pageable pageable = PageRequest.of(
                page, size, 
                Sort.by(Sort.Direction.fromString(sortDir), sortBy)
        );
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        PagedResponse<CommunityPostResponse> response = communityService.getAllPosts(pageable, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/posts/{postId}")
    public ResponseEntity<CommunityPostResponse> getPostById(
            @PathVariable Integer postId,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        CommunityPostResponse response = communityService.getPostById(postId, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/posts")
    public ResponseEntity<CommunityPostResponse> createPost(
            @Valid @RequestBody CommunityPostRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        CommunityPostResponse response = communityService.createPost(request, userId);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/posts/{postId}")
    public ResponseEntity<CommunityPostResponse> updatePost(
            @PathVariable Integer postId,
            @Valid @RequestBody CommunityPostRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        CommunityPostResponse response = communityService.updatePost(postId, request, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Integer postId,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        communityService.deletePost(postId, userId);
        
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<CommunityPostResponse> likePost(
            @PathVariable Integer postId,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        CommunityPostResponse response = communityService.likePost(postId, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/posts/{postId}/unlike")
    public ResponseEntity<CommunityPostResponse> unlikePost(
            @PathVariable Integer postId,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        CommunityPostResponse response = communityService.unlikePost(postId, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/posts/search")
    public ResponseEntity<PagedResponse<CommunityPostResponse>> searchPosts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Pageable pageable = PageRequest.of(page, size);
        Integer userId = Integer.valueOf(userDetails.getUsername());
        PagedResponse<CommunityPostResponse> response = communityService.searchPosts(keyword, pageable, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/posts/tag/{tag}")
    public ResponseEntity<PagedResponse<CommunityPostResponse>> getPostsByTag(
            @PathVariable String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Pageable pageable = PageRequest.of(page, size);
        Integer userId = Integer.valueOf(userDetails.getUsername());
        PagedResponse<CommunityPostResponse> response = communityService.getPostsByTag(tag, pageable, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/posts/most-liked")
    public ResponseEntity<PagedResponse<CommunityPostResponse>> getMostLikedPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Pageable pageable = PageRequest.of(page, size);
        Integer userId = Integer.valueOf(userDetails.getUsername());
        PagedResponse<CommunityPostResponse> response = communityService.getMostLikedPosts(pageable, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/posts/most-commented")
    public ResponseEntity<PagedResponse<CommunityPostResponse>> getMostCommentedPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Pageable pageable = PageRequest.of(page, size);
        Integer userId = Integer.valueOf(userDetails.getUsername());
        PagedResponse<CommunityPostResponse> response = communityService.getMostCommentedPosts(pageable, userId);
        
        return ResponseEntity.ok(response);
    }
    
    // Comment endpoints
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<PagedResponse<CommentResponse>> getCommentsByPostId(
            @PathVariable Integer postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Pageable pageable = PageRequest.of(page, size);
        Integer userId = Integer.valueOf(userDetails.getUsername());
        PagedResponse<CommentResponse> response = communityService.getCommentsByPostId(postId, pageable, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Integer postId,
            @Valid @RequestBody CommentRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        CommentResponse response = communityService.addComment(postId, request, userId);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Integer commentId,
            @Valid @RequestBody CommentRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        CommentResponse response = communityService.updateComment(commentId, request, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Integer commentId,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        communityService.deleteComment(commentId, userId);
        
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/comments/{commentId}/like")
    public ResponseEntity<CommentResponse> likeComment(
            @PathVariable Integer commentId,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        CommentResponse response = communityService.likeComment(commentId, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/comments/{commentId}/unlike")
    public ResponseEntity<CommentResponse> unlikeComment(
            @PathVariable Integer commentId,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        CommentResponse response = communityService.unlikeComment(commentId, userId);
        
        return ResponseEntity.ok(response);
    }
    
    // Event endpoints
    @GetMapping("/events/upcoming")
    public ResponseEntity<PagedResponse<CommunityEventResponse>> getUpcomingEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Pageable pageable = PageRequest.of(page, size);
        Integer userId = Integer.valueOf(userDetails.getUsername());
        PagedResponse<CommunityEventResponse> response = communityService.getUpcomingEvents(pageable, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/events/ongoing")
    public ResponseEntity<PagedResponse<CommunityEventResponse>> getOngoingEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Pageable pageable = PageRequest.of(page, size);
        Integer userId = Integer.valueOf(userDetails.getUsername());
        PagedResponse<CommunityEventResponse> response = communityService.getOngoingEvents(pageable, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/events/past")
    public ResponseEntity<PagedResponse<CommunityEventResponse>> getPastEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Pageable pageable = PageRequest.of(page, size);
        Integer userId = Integer.valueOf(userDetails.getUsername());
        PagedResponse<CommunityEventResponse> response = communityService.getPastEvents(pageable, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/events/{eventId}")
    public ResponseEntity<CommunityEventResponse> getEventById(
            @PathVariable Integer eventId,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        CommunityEventResponse response = communityService.getEventById(eventId, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/events")
    public ResponseEntity<CommunityEventResponse> createEvent(
            @Valid @RequestBody CommunityEventRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        CommunityEventResponse response = communityService.createEvent(request, userId);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/events/{eventId}")
    public ResponseEntity<CommunityEventResponse> updateEvent(
            @PathVariable Integer eventId,
            @Valid @RequestBody CommunityEventRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        CommunityEventResponse response = communityService.updateEvent(eventId, request, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable Integer eventId,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        communityService.deleteEvent(eventId, userId);
        
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/events/{eventId}/attend")
    public ResponseEntity<CommunityEventResponse> attendEvent(
            @PathVariable Integer eventId,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        CommunityEventResponse response = communityService.attendEvent(eventId, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/events/{eventId}/unattend")
    public ResponseEntity<CommunityEventResponse> unattendEvent(
            @PathVariable Integer eventId,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        CommunityEventResponse response = communityService.unattendEvent(eventId, userId);
        
        return ResponseEntity.ok(response);
    }
    
    // Challenge endpoints
    @GetMapping("/challenges/active")
    public ResponseEntity<PagedResponse<CommunityChallengeResponse>> getActiveChallenges(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Pageable pageable = PageRequest.of(page, size);
        Integer userId = Integer.valueOf(userDetails.getUsername());
        PagedResponse<CommunityChallengeResponse> response = communityService.getActiveChallenges(pageable, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/challenges/upcoming")
    public ResponseEntity<PagedResponse<CommunityChallengeResponse>> getUpcomingChallenges(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Pageable pageable = PageRequest.of(page, size);
        Integer userId = Integer.valueOf(userDetails.getUsername());
        PagedResponse<CommunityChallengeResponse> response = communityService.getUpcomingChallenges(pageable, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/challenges/completed")
    public ResponseEntity<PagedResponse<CommunityChallengeResponse>> getCompletedChallenges(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Pageable pageable = PageRequest.of(page, size);
        Integer userId = Integer.valueOf(userDetails.getUsername());
        PagedResponse<CommunityChallengeResponse> response = communityService.getCompletedChallenges(pageable, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/challenges/{challengeId}")
    public ResponseEntity<CommunityChallengeResponse> getChallengeById(
            @PathVariable Integer challengeId,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        CommunityChallengeResponse response = communityService.getChallengeById(challengeId, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/challenges")
    public ResponseEntity<CommunityChallengeResponse> createChallenge(
            @Valid @RequestBody CommunityChallengeRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        CommunityChallengeResponse response = communityService.createChallenge(request, userId);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/challenges/{challengeId}")
    public ResponseEntity<CommunityChallengeResponse> updateChallenge(
            @PathVariable Integer challengeId,
            @Valid @RequestBody CommunityChallengeRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        CommunityChallengeResponse response = communityService.updateChallenge(challengeId, request, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/challenges/{challengeId}")
    public ResponseEntity<Void> deleteChallenge(
            @PathVariable Integer challengeId,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        communityService.deleteChallenge(challengeId, userId);
        
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/challenges/{challengeId}/join")
    public ResponseEntity<CommunityChallengeResponse> joinChallenge(
            @PathVariable Integer challengeId,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        CommunityChallengeResponse response = communityService.joinChallenge(challengeId, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/challenges/{challengeId}/leave")
    public ResponseEntity<CommunityChallengeResponse> leaveChallenge(
            @PathVariable Integer challengeId,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Integer userId = Integer.valueOf(userDetails.getUsername());
        CommunityChallengeResponse response = communityService.leaveChallenge(challengeId, userId);
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/challenges/{challengeId}/progress")
    public ResponseEntity<CommunityChallengeResponse> updateChallengeProgress(
            @PathVariable Integer challengeId,
            @RequestParam Integer progress,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        // Only admin or challenge creator should be able to update progress
        CommunityChallengeResponse response = communityService.updateChallengeProgress(challengeId, progress);
        
        return ResponseEntity.ok(response);
    }
}

