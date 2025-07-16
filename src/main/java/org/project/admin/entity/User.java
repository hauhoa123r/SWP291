package org.project.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.project.admin.enums.users.UserRole;
import org.project.admin.enums.users.UserStatus;

@Getter
@Setter
@Table(name = "users")
@Entity
// Cấu hình Hibernate để tự động cập nhật cột 'deleted' khi thực hiện lệnh DELETE
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE user_id=?")
// Tự động lọc các bản ghi chưa bị xóa mềm trong mọi câu truy vấn
//@Where(clause = "deleted = false")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    private UserStatus userStatus;

    @Column(name = "verification_token")
    private String verificationToken;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "two_factor_enabled")
    private Boolean twoFactorEnabled;

    @Column(name = "deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean deleted = false;
}