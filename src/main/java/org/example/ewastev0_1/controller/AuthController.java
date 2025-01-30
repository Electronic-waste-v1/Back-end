package org.example.ewastev0_1.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ewastev0_1.config.JwtTokenUtil;
import org.example.ewastev0_1.dto.request.Loginrequest;
import org.example.ewastev0_1.dto.request.UserRequest;
import org.example.ewastev0_1.services.Interface.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("")
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


}
