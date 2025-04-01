package org.example.ewastev0_1.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ewastev0_1.domain.entites.User;
import org.example.ewastev0_1.dto.request.*;
import org.example.ewastev0_1.dto.response.*;
import org.example.ewastev0_1.repository.UserRepository;
import org.example.ewastev0_1.services.Interface.CommunityService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;
    private final UserRepository userRepository;


    @GetMapping("/posts")
    public PagedResponse<CommunityPostResponse> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String search,
            @AuthenticationPrincipal UserDetails userDetails) {
        Integer currentUserId = getUserIdFromPrincipal(userDetails);
        Pageable pageable;
        if ("recent".equals(sortBy)) {
            pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        } else {
            pageable = PageRequest.of(page, size);
        }

        if (search != null && !search.isEmpty()) {
            return communityService.searchPosts(search, pageable, currentUserId);
        } else {
            return communityService.getAllPosts(pageable, currentUserId);
        }
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<CommunityPostResponse> getPostById(
            @PathVariable Integer postId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = getUserIdFromPrincipal(userDetails);
        CommunityPostResponse response = communityService.getPostById(postId, userId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/posts")
    public ResponseEntity<CommunityPostResponse> createPost(
            @Valid @RequestBody CommunityPostRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = getUserIdFromPrincipal(userDetails);
        CommunityPostResponse response = communityService.createPost(request, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<CommunityPostResponse> updatePost(
            @PathVariable Integer postId,
            @Valid @RequestBody CommunityPostRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = getUserIdFromPrincipal(userDetails);
        CommunityPostResponse response = communityService.updatePost(postId, request, userId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Integer postId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = getUserIdFromPrincipal(userDetails);
        communityService.deletePost(postId, userId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<CommunityPostResponse> likePost(
            @PathVariable Integer postId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = getUserIdFromPrincipal(userDetails);
        CommunityPostResponse response = communityService.likePost(postId, userId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/posts/{postId}/unlike")
    public ResponseEntity<CommunityPostResponse> unlikePost(
            @PathVariable Integer postId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = getUserIdFromPrincipal(userDetails);
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
        Integer userId = getUserIdFromPrincipal(userDetails);
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
        Integer userId = getUserIdFromPrincipal(userDetails);
        PagedResponse<CommunityPostResponse> response = communityService.getPostsByTag(tag, pageable, userId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/posts/most-liked")
    public ResponseEntity<PagedResponse<CommunityPostResponse>> getMostLikedPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {

        Pageable pageable = PageRequest.of(page, size);
        Integer userId = getUserIdFromPrincipal(userDetails);
        PagedResponse<CommunityPostResponse> response = communityService.getMostLikedPosts(pageable, userId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/posts/most-commented")
    public ResponseEntity<PagedResponse<CommunityPostResponse>> getMostCommentedPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {

        Pageable pageable = PageRequest.of(page, size);
        Integer userId = getUserIdFromPrincipal(userDetails);
        PagedResponse<CommunityPostResponse> response = communityService.getMostCommentedPosts(pageable, userId);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<PagedResponse<CommentResponse>> getCommentsByPostId(
            @PathVariable Integer postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {

        Pageable pageable = PageRequest.of(page, size);
        Integer userId = getUserIdFromPrincipal(userDetails);
        PagedResponse<CommentResponse> response = communityService.getCommentsByPostId(postId, pageable, userId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Integer postId,
            @Valid @RequestBody CommentRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = getUserIdFromPrincipal(userDetails);
        CommentResponse response = communityService.addComment(postId, request, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Integer commentId,
            @Valid @RequestBody CommentRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = getUserIdFromPrincipal(userDetails);
        CommentResponse response = communityService.updateComment(commentId, request, userId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Integer commentId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = getUserIdFromPrincipal(userDetails);
        communityService.deleteComment(commentId, userId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/comments/{commentId}/like")
    public ResponseEntity<CommentResponse> likeComment(
            @PathVariable Integer commentId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = getUserIdFromPrincipal(userDetails);
        CommentResponse response = communityService.likeComment(commentId, userId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/comments/{commentId}/unlike")
    public ResponseEntity<CommentResponse> unlikeComment(
            @PathVariable Integer commentId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = getUserIdFromPrincipal(userDetails);
        CommentResponse response = communityService.unlikeComment(commentId, userId);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/events/upcoming")
    public ResponseEntity<PagedResponse<CommunityEventResponse>> getUpcomingEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {

        Pageable pageable = PageRequest.of(page, size);
        Integer userId = getUserIdFromPrincipal(userDetails);
        PagedResponse<CommunityEventResponse> response = communityService.getUpcomingEvents(pageable, userId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/events/ongoing")
    public ResponseEntity<PagedResponse<CommunityEventResponse>> getOngoingEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {

        Pageable pageable = PageRequest.of(page, size);
        Integer userId = getUserIdFromPrincipal(userDetails);
        PagedResponse<CommunityEventResponse> response = communityService.getOngoingEvents(pageable, userId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/events/past")
    public ResponseEntity<PagedResponse<CommunityEventResponse>> getPastEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {

        Pageable pageable = PageRequest.of(page, size);
        Integer userId = getUserIdFromPrincipal(userDetails);
        PagedResponse<CommunityEventResponse> response = communityService.getPastEvents(pageable, userId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<CommunityEventResponse> getEventById(
            @PathVariable Integer eventId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = getUserIdFromPrincipal(userDetails);
        CommunityEventResponse response = communityService.getEventById(eventId, userId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/events")
    public ResponseEntity<CommunityEventResponse> createEvent(
            @Valid @RequestBody CommunityEventRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
log.info("request"+ request);
        Integer userId = getUserIdFromPrincipal(userDetails);
        CommunityEventResponse response = communityService.createEvent(request, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/events/{eventId}")
    public ResponseEntity<CommunityEventResponse> updateEvent(
            @PathVariable Integer eventId,
            @Valid @RequestBody CommunityEventRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = getUserIdFromPrincipal(userDetails);
        CommunityEventResponse response = communityService.updateEvent(eventId, request, userId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable Integer eventId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = getUserIdFromPrincipal(userDetails);
        communityService.deleteEvent(eventId, userId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/events/{eventId}/attend")
    public ResponseEntity<CommunityEventResponse> attendEvent(
            @PathVariable Integer eventId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = getUserIdFromPrincipal(userDetails);
        CommunityEventResponse response = communityService.attendEvent(eventId, userId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/events/{eventId}/unattend")
    public ResponseEntity<CommunityEventResponse> unattendEvent(
            @PathVariable Integer eventId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = getUserIdFromPrincipal(userDetails);
        CommunityEventResponse response = communityService.unattendEvent(eventId, userId);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/challenges/active")
    public ResponseEntity<PagedResponse<CommunityChallengeResponse>> getActiveChallenges(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {

        Pageable pageable = PageRequest.of(page, size);
        Integer userId = getUserIdFromPrincipal(userDetails);
        PagedResponse<CommunityChallengeResponse> response = communityService.getActiveChallenges(pageable, userId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/challenges/upcoming")
    public ResponseEntity<PagedResponse<CommunityChallengeResponse>> getUpcomingChallenges(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {

        Pageable pageable = PageRequest.of(page, size);
        Integer userId = getUserIdFromPrincipal(userDetails);
        PagedResponse<CommunityChallengeResponse> response = communityService.getUpcomingChallenges(pageable, userId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/challenges/completed")
    public ResponseEntity<PagedResponse<CommunityChallengeResponse>> getCompletedChallenges(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {

        Pageable pageable = PageRequest.of(page, size);
        Integer userId = getUserIdFromPrincipal(userDetails);
        PagedResponse<CommunityChallengeResponse> response = communityService.getCompletedChallenges(pageable, userId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/challenges/{challengeId}")
    public ResponseEntity<CommunityChallengeResponse> getChallengeById(
            @PathVariable Integer challengeId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = getUserIdFromPrincipal(userDetails);
        CommunityChallengeResponse response = communityService.getChallengeById(challengeId, userId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/challenges")
    public ResponseEntity<CommunityChallengeResponse> createChallenge(
            @Valid @RequestBody CommunityChallengeRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = getUserIdFromPrincipal(userDetails);
        CommunityChallengeResponse response = communityService.createChallenge(request, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/challenges/{challengeId}")
    public ResponseEntity<CommunityChallengeResponse> updateChallenge(
            @PathVariable Integer challengeId,
            @Valid @RequestBody CommunityChallengeRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = getUserIdFromPrincipal(userDetails);
        CommunityChallengeResponse response = communityService.updateChallenge(challengeId, request, userId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/challenges/{challengeId}")
    public ResponseEntity<Void> deleteChallenge(
            @PathVariable Integer challengeId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = getUserIdFromPrincipal(userDetails);
        communityService.deleteChallenge(challengeId, userId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/challenges/{challengeId}/join")
    public ResponseEntity<CommunityChallengeResponse> joinChallenge(
            @PathVariable Integer challengeId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = getUserIdFromPrincipal(userDetails);
        CommunityChallengeResponse response = communityService.joinChallenge(challengeId, userId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/challenges/{challengeId}/leave")
    public ResponseEntity<CommunityChallengeResponse> leaveChallenge(
            @PathVariable Integer challengeId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Integer userId = getUserIdFromPrincipal(userDetails);
        CommunityChallengeResponse response = communityService.leaveChallenge(challengeId, userId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/challenges/{challengeId}/progress")
    public ResponseEntity<CommunityChallengeResponse> updateChallengeProgress(
            @PathVariable Integer challengeId,
            @RequestParam Integer progress,
            @AuthenticationPrincipal UserDetails userDetails) {


        CommunityChallengeResponse response = communityService.updateChallengeProgress(challengeId, progress);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/events")
    public ResponseEntity<PagedResponse<CommunityEventResponse>> getEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Boolean upcoming,
            @RequestParam(required = false) Boolean ongoing,
            @RequestParam(required = false) Boolean past,
            @AuthenticationPrincipal UserDetails userDetails) {

        Pageable pageable = PageRequest.of(page, size);
        Integer userId = getUserIdFromPrincipal(userDetails);

        PagedResponse<CommunityEventResponse> response;

        if (upcoming != null && upcoming) {
            response = communityService.getUpcomingEvents(pageable, userId);
        } else if (ongoing != null && ongoing) {
            response = communityService.getOngoingEvents(pageable, userId);
        } else if (past != null && past) {
            response = communityService.getPastEvents(pageable, userId);
        } else {

            response = communityService.getUpcomingEvents(pageable, userId);
        }

        return ResponseEntity.ok(response);
    }

    // New endpoint for challenges with filter parameters
    @GetMapping("/challenges")
    public ResponseEntity<PagedResponse<CommunityChallengeResponse>> getChallenges(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) Boolean upcoming,
            @RequestParam(required = false) Boolean completed,
            @AuthenticationPrincipal UserDetails userDetails) {

        Pageable pageable = PageRequest.of(page, size);
        Integer userId = getUserIdFromPrincipal(userDetails);

        PagedResponse<CommunityChallengeResponse> response;

        if (active != null && active) {
            response = communityService.getActiveChallenges(pageable, userId);
        } else if (upcoming != null && upcoming) {
            response = communityService.getUpcomingChallenges(pageable, userId);
        } else if (completed != null && completed) {
            response = communityService.getCompletedChallenges(pageable, userId);
        } else {

            response = communityService.getActiveChallenges(pageable, userId);
        }

        return ResponseEntity.ok(response);
    }


    private Integer getUserIdFromPrincipal(UserDetails userDetails) {

        String username = userDetails.getUsername();


        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));


        return user.getId();
    }
}

