package org.example.ewastev0_1.controller;

import lombok.RequiredArgsConstructor;
import org.example.ewastev0_1.dto.response.UserPointsResponse;
import org.example.ewastev0_1.services.Interface.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}/points")
    public ResponseEntity<UserPointsResponse> getUserPoints(@PathVariable Integer userId) {
        UserPointsResponse userPoints = userService.getUserPoints(userId);
        return ResponseEntity.ok(userPoints);
    }

    @PostMapping("/{userId}/points")
    public ResponseEntity<UserPointsResponse> updateUserPoints(
            @PathVariable Integer userId,
            @RequestParam(required = false) Integer pointsToAdd,
            @RequestParam(required = false) Integer pointsToUse) {

        UserPointsResponse updatedPoints = userService.updateUserPoints(userId, pointsToAdd, pointsToUse);
        return ResponseEntity.ok(updatedPoints);
    }
}

