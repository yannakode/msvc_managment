package com.microservice.gateway.services.impl;

import com.microservice.gateway.models.UserEntity;
import com.microservice.gateway.models.dtos.UserRequest;
import com.microservice.gateway.repositories.UserRepository;
import com.microservice.gateway.security.util.JwtUtils;
import com.microservice.gateway.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public UserEntity createUser(UserRequest userRequest){
        UserEntity user = new UserEntity();
        user.setUsername(userRequest.username());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        return userRepository.save(user);
    }
}
