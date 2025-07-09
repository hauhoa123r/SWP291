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
    private StaffEntity staffEntity; // Assuming StaffEntity exists

    @OneToMany(mappedBy = "userEntity")
    private Set<CartItemEntity> cartItemEntities = new LinkedHashSet<>(); // Assuming CartItemEntity exists

    @OneToMany(mappedBy = "userEntity")
    private Set<NotificationEntity> notificationEntities = new LinkedHashSet<>(); // Assuming NotificationEntity exists

    @OneToMany(mappedBy = "userEntity")
    private Set<PatientEntity> patientEntities = new LinkedHashSet<>(); // Assuming PatientEntity exists

    @OneToMany(mappedBy = "userEntity")
    private Set<ShippingAddressEntity> shippingAddressEntities = new LinkedHashSet<>(); // Assuming ShippingAddressEntity exists

    @OneToMany(mappedBy = "userEntity")
    private Set<UserCouponEntity> userCouponEntities = new LinkedHashSet<>(); // Assuming UserCouponEntity exists

    @OneToMany(mappedBy = "user") // Chỉnh lại mappedBy theo tên trường trong WalletEntity
    private Set<WalletEntity> walletEntities = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "wishlist_products",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<ProductEntity> products = new LinkedHashSet<>(); // Assuming ProductEntity exists

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id")
    private PatientEntity patientEntity; // Assuming PatientEntity exists

}