package org.example.ewastev0_1.services.Impl;

import lombok.RequiredArgsConstructor;
import org.example.ewastev0_1.config.JwtTokenUtil;
import org.example.ewastev0_1.domain.entites.User;
import org.example.ewastev0_1.dto.request.UserRequest;
import org.example.ewastev0_1.dto.response.UserResponse;
import org.example.ewastev0_1.mapper.UserMapper;
import org.example.ewastev0_1.repository.UserRepository;
import org.example.ewastev0_1.services.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    @Override
    public UserResponse register(UserRequest userResquest) {

        User user=userMapper.toEntity(userResquest);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);


        User savedUser = userRepository.save(user);



        return userMapper.toResponse(savedUser);
    }
}
