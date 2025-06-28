package org.project.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "wallet")
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")  // Đặt tên cột cho khóa chính
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // Quan hệ nhiều - một với UserEntity
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "fk_wallet_user"))
    // Liên kết với bảng users
    private UserEntity user;

    @Column(name = "balance", nullable = false, precision = 10, scale = 2)  // Đặt các thuộc tính cột balance
    private BigDecimal balance;

    // Getter và Setter cho các thuộc tính
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
