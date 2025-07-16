package org.project.admin.dto.request;

import org.project.admin.enums.users.UserRole;
import org.project.admin.enums.users.UserStatus;
import lombok.Data;

@Data
public class UserSearchRequest {
    private Long userId;
    private String email;
    private String phoneNumber;
    private UserRole userRole;
    private UserStatus userStatus;
    private Boolean isVerified;
    private Boolean twoFactorEnabled;
}
