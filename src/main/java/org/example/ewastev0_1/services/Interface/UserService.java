package org.example.ewastev0_1.services.Interface;

import org.example.ewastev0_1.domain.entites.ActionHistorique;
import org.example.ewastev0_1.dto.request.UserRequest;
import org.example.ewastev0_1.dto.response.ActionHistoryResponse;
import org.example.ewastev0_1.dto.response.UserPointsResponse;
import org.example.ewastev0_1.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse register(UserRequest userRequest);
    UserResponse getCurrentUser();
     UserResponse getUserByUsername(String username);
    UserPointsResponse getUserPoints(Integer userId);
    UserPointsResponse updateUserPoints(Integer userId, Integer pointsToAdd, Integer pointsToUse);
    List<ActionHistoryResponse> getUserActionHistory(Integer userId);
}
