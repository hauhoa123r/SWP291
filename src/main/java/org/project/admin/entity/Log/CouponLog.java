package org.project.admin.entity.Log;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.project.admin.enums.AuditAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupon_logs")
@Getter
@Setter
public class CouponLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_id")
    private Long couponId;

    @Enumerated(EnumType.STRING)
    private AuditAction action; // CREATE, UPDATE, DELETE

    @Column(name = "log_time")
    private LocalDateTime logTime;

    @Column(name = "log_detail", columnDefinition = "TEXT")
    private String logDetail;
}

