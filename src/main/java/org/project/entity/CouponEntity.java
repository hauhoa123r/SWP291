package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "coupons", schema = "swp391")
public class CouponEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Lob
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "value", nullable = false, precision = 10, scale = 2)
    private BigDecimal value;

    @ColumnDefault("0.00")
    @Column(name = "minimum_order_amount", precision = 10, scale = 2)
    private BigDecimal minimumOrderAmount;

    @NotNull
    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @OneToMany
    private Set<OrderEntity> orderEntities = new LinkedHashSet<>();
    @OneToMany
    private Set<UserCouponEntity> userCouponEntities = new LinkedHashSet<>();

/*
 TODO [Reverse Engineering] create field to map the 'discount_type' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "discount_type", columnDefinition = "enum not null")
    private Object discountType;
*/
}