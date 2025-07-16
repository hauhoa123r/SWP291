package org.project.admin.service;

import org.project.admin.dto.request.UserRequest;
import org.project.admin.dto.request.UserSearchRequest;
import org.project.admin.dto.response.UserResponse;
import org.project.admin.util.PageResponse;
import org.springframework.data.domain.Pageable;


public interface UserService {
    UserResponse createUser(UserRequest dto);
    UserResponse updateUser(Long id, UserRequest dto);
    void deleteUser(Long id);
    UserResponse getUserById(Long id);
    PageResponse<UserResponse> getAllUsers(Pageable pageable);
    PageResponse<UserResponse> search(UserSearchRequest request, int page, int size);
}

