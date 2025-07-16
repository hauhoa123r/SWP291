package org.project.admin.dto.request;

import org.project.admin.enums.users.UserRole;
import org.project.admin.enums.users.UserStatus;
import lombok.Data;

@Data
public class UserRequest {
    private UserRole userRole;
    private String phoneNumber;
    private String email;
    private String password; // Plaintext for register/update
    private UserStatus userStatus;
    private Boolean twoFactorEnabled;
}

