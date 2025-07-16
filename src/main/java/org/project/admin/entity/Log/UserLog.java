package org.project.admin.entity.Log;


import org.project.admin.enums.AuditAction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users_logs")
@Getter
@Setter
public class UserLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    private AuditAction action; // CREATE, UPDATE, DELETE

    @Column(name = "log_time")
    private LocalDateTime logTime;

    @Column(name = "log_detail", columnDefinition = "TEXT")
    private String logDetail;
}
