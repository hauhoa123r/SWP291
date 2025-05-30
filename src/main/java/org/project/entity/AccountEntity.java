package org.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "accounts")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountEntity {

    @Column(name = "account_id")
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "create_at")
    private Date createdAt;

    @OneToMany(mappedBy = "accountEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PharmacyStaffEntity> pharmacyStaffEntities;
}
