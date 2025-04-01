package org.example.ewastev0_1.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ewastev0_1.config.JwtTokenUtil;
import org.example.ewastev0_1.domain.entites.ActionHistorique;
import org.example.ewastev0_1.dto.request.Loginrequest;
import org.example.ewastev0_1.dto.request.UserRequest;
import org.example.ewastev0_1.dto.response.ActionHistoryResponse;
import org.example.ewastev0_1.dto.response.UserPointsResponse;
import org.example.ewastev0_1.dto.response.UserResponse;
import org.example.ewastev0_1.services.Interface.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final UserService userService;



    private final AuthenticationManager authenticationManager;


    private  final JwtTokenUtil jwtTokenUtil;



    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody Loginrequest loginDTO) {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    @PostMapping("/register")

    public ResponseEntity<?> register(@Valid @RequestBody UserRequest registerDTO) {
        userService.register(registerDTO);
        return ResponseEntity.ok(Collections.singletonMap("message", "User registered successfully"));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> getCurrentUser() {

        UserResponse user = userService.getCurrentUser();
        return ResponseEntity.ok(user);
    }

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

    @GetMapping("/{userId}/actions")
    public ResponseEntity<List<ActionHistoryResponse>> getUserActionHistory(@PathVariable Integer userId) {
        List<ActionHistoryResponse> response = userService.getUserActionHistory(userId);
        return ResponseEntity.ok(response);
    }

}
