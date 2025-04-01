package org.example.ewastev0_1.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ewastev0_1.config.JwtTokenUtil;
import org.example.ewastev0_1.domain.entites.ActionHistorique;
import org.example.ewastev0_1.domain.entites.User;
import org.example.ewastev0_1.domain.entites.UserPoints;
import org.example.ewastev0_1.dto.request.UserRequest;
import org.example.ewastev0_1.dto.response.ActionHistoryResponse;
import org.example.ewastev0_1.dto.response.UserPointsResponse;
import org.example.ewastev0_1.dto.response.UserResponse;
import org.example.ewastev0_1.exception.ResourceNotFoundException;
import org.example.ewastev0_1.mapper.UserMapper;
import org.example.ewastev0_1.repository.ActionHistoriqueRepository;
import org.example.ewastev0_1.repository.UserPointsRepository;
import org.example.ewastev0_1.repository.UserRepository;
import org.example.ewastev0_1.services.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
private  final ActionHistoriqueRepository actionHistoriqueRepository;

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserPointsRepository userPointsRepository;
    @Override
    public UserResponse register(UserRequest userRequest) {

        User user = userMapper.toEntity(userRequest);


        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);


        User savedUser = userRepository.save(user);
        log.info("saveUser",savedUser);

        UserPoints userPoints = UserPoints.builder()
                .user(savedUser)
                .pointsTotal(0)
                .pointsUtilises(0)
                .build();
        userPointsRepository.save(userPoints);


        savedUser.setUserPoints(userPoints);
        userRepository.save(savedUser);


        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String currentUsername = authentication.getName();
        log.info("Current user: {}", currentUsername);

        return getUserByUsername(currentUsername);
    }


    @Override
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return userMapper.toResponse(user);
    }


    @Override
    public UserPointsResponse getUserPoints(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        UserPoints userPoints = userPointsRepository.findByUser(user)
                .orElseGet(() -> {

                    UserPoints newPoints = UserPoints.builder()
                            .user(user)
                            .pointsTotal(0)
                            .pointsUtilises(0)
                            .build();
                    return userPointsRepository.save(newPoints);
                });

        return mapToUserPointsResponse(userPoints);
    }

    @Override
    @Transactional
    public UserPointsResponse updateUserPoints(Integer userId, Integer pointsToAdd, Integer pointsToUse) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        UserPoints userPoints = userPointsRepository.findByUser(user)
                .orElseGet(() -> {

                    UserPoints newPoints = UserPoints.builder()
                            .user(user)
                            .pointsTotal(0)
                            .pointsUtilises(0)
                            .build();
                    return userPointsRepository.save(newPoints);
                });


        if (pointsToAdd != null && pointsToAdd > 0) {
            userPoints.setPointsTotal(userPoints.getPointsTotal() + pointsToAdd);
        }

        if (pointsToUse != null && pointsToUse > 0) {
            int availablePoints = userPoints.getPointsTotal() - userPoints.getPointsUtilises();
            if (availablePoints < pointsToUse) {
                throw new RuntimeException("Not enough points available");
            }
            userPoints.setPointsUtilises(userPoints.getPointsUtilises() + pointsToUse);
        }

        userPointsRepository.save(userPoints);

        return mapToUserPointsResponse(userPoints);
    }


    @Override
    public List<ActionHistoryResponse> getUserActionHistory(Integer userId) {
        List<ActionHistorique> actions = actionHistoriqueRepository.findByUser_Id(userId);
        return actions.stream()
                .map(userMapper::toActionHistoryResponse)
                .collect(Collectors.toList());
    }


    private UserPointsResponse mapToUserPointsResponse(UserPoints userPoints) {
        return userMapper.toUserPointsResponse(userPoints);
    }

}
