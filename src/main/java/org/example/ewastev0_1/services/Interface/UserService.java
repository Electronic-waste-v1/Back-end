package org.example.ewastev0_1.services.Interface;

import org.example.ewastev0_1.dto.request.UserRequest;
import org.example.ewastev0_1.dto.response.UserResponse;

public interface UserService {
    UserResponse register(UserRequest userRequest);
}
