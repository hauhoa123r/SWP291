package org.project.admin.dto.response;

import org.project.admin.enums.users.UserRole;
import org.project.admin.enums.users.UserStatus;
import lombok.Data;

@Data
public class UserResponse {
    private Long userId;
    private UserRole userRole;
    private String phoneNumber;
    private String email;
    private UserStatus userStatus;
    private Boolean isVerified;
    private Boolean twoFactorEnabled;
}
