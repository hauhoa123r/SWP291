package org.project.service.impl;


import org.project.dto.request.LoginRequest;
import org.project.dto.response.Response;
import org.project.entity.UserEntity;

public interface IUserService {
    Response register(UserEntity user);
    Response login(LoginRequest loginRequest);
}
