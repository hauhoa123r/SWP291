package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users", schema = "swp391")
public class UserEntity {
    @Id
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @Size(max = 255)
    @Column(name = "password_hash")
    private String passwordHash;

    @Size(max = 255)
    @Column(name = "phone_number")
    private String phoneNumber;

    @Size(max = 255)
    @Column(name = "verification_token")
    private String verificationToken;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "two_factor_enabled")
    private Boolean twoFactorEnabled;

    @OneToOne(mappedBy = "userEntity")
    private StaffEntity staffEntity;

    @OneToMany(mappedBy = "userEntity")
    private Set<CartItemEntity> cartItemEntities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "userEntity")
    private Set<NotificationEntity> notificationEntities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "userEntity")
    private Set<PatientEntity> patientEntities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "userEntity")
    private Set<ShippingAddressEntity> shippingAddressEntities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "userEntity")
    private Set<UserCouponEntity> userCouponEntities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "userEntity")
    private Set<WalletEntity> walletEntities = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "wishlist_products",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<ProductEntity> products = new LinkedHashSet<>();


/*
 TODO [Reverse Engineering] create field to map the 'status' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "status", columnDefinition = "enum")
    private Object status;
*/
/*
 TODO [Reverse Engineering] create field to map the 'user_role' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @org.hibernate.annotations.ColumnDefault("'PATIENT'")
    @Column(name = "user_role", columnDefinition = "enum not null")
    private java.lang.Object userRole;
*/
/*
 TODO [Reverse Engineering] create field to map the 'user_status' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @org.hibernate.annotations.ColumnDefault("'ACTIVE'")
    @Column(name = "user_status", columnDefinition = "enum not null")
    private java.lang.Object userStatus;
*/
}