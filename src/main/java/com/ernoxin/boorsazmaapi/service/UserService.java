package com.ernoxin.boorsazmaapi.service;

import com.ernoxin.boorsazmaapi.dto.UserCreateRequest;
import com.ernoxin.boorsazmaapi.dto.UserResponse;
import com.ernoxin.boorsazmaapi.dto.UserUpdateRequest;

import java.util.List;

public interface UserService {
    UserResponse create(UserCreateRequest request);

    UserResponse getById(Long id);

    List<UserResponse> getAll();

    UserResponse update(UserUpdateRequest request);

    void delete(Long id);
}
