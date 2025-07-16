package org.project.admin.service.impl;

import org.project.admin.dto.request.UserRequest;
import org.project.admin.dto.request.UserSearchRequest;
import org.project.admin.dto.response.UserResponse;
import org.project.admin.entity.User;
import org.project.admin.enums.AuditAction;
import org.project.admin.enums.users.UserStatus;
import org.project.admin.mapper.UserMapper;
import org.project.admin.repository.UserRepository;
import org.project.admin.service.Log.UserLogService;
import org.project.admin.service.UserService;
import org.project.admin.specification.UserSpecification;
import org.project.admin.util.PageResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service("adminUserServiceImpl")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserLogService userLogService;

    @Override
    public UserResponse createUser(UserRequest dto) {
        User user = userMapper.toEntity(dto);
        if (dto.getPassword() != null) {
            user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }
        user.setIsVerified(false);
        User saved = userRepository.save(user);

        // Ghi log tạo mới
        userLogService.logUserAction(saved, AuditAction.CREATE);
        return userMapper.toResponse(saved);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy người dùng"));
        // Lưu trạng thái cũ để log
        UserResponse oldUser = userMapper.toResponse(user);

        userMapper.updateEntityFromDto(dto, user);
        if (dto.getPassword() != null) {
            user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }
        User saved = userRepository.save(user);

        // Ghi log update
        UserResponse newUser = userMapper.toResponse(saved);
        userLogService.logUserUpdateAction(oldUser, newUser, AuditAction.UPDATE);

        return newUser;
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy người dùng"));

        // Đánh dấu xóa mềm
        user.setDeleted(true);
        user.setUserStatus(UserStatus.INACTIVE);
        userRepository.save(user);  // Cập nhật

        userLogService.logUserAction(user, AuditAction.DELETE);
    }


    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy người dùng"));
        return userMapper.toResponse(user);
    }

    @Override
    public PageResponse<UserResponse> getAllUsers(Pageable pageable) {
        Page<UserResponse> pageData = userRepository.findAllByDeletedFalse(pageable)
                .map(userMapper::toResponse);
        return new PageResponse<>(pageData);
    }

    @Override
    public PageResponse<UserResponse> search(UserSearchRequest request, int page, int size) {
        Specification<User> spec = UserSpecification.filter(request)
                .and((root, query, cb) -> cb.equal(root.get("deleted"), false));

        Page<User> userPage = userRepository.findAll(spec, PageRequest.of(page, size));
        Page<UserResponse> mappedPage = userPage.map(userMapper::toResponse);
        return new PageResponse<>(mappedPage);
    }

}
