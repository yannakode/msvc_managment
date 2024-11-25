package com.microservice.gateway.services;

import com.microservice.gateway.models.UserEntity;
import com.microservice.gateway.models.dtos.UserRequest;

public interface UserService {
    UserEntity createUser(UserRequest userRequest);
}
