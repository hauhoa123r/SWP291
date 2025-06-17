package org.project.service;

import org.project.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    Optional<UserEntity> getUserByEmail(String email);
}
