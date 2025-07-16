package org.project.admin.entity.Log;


import org.project.admin.enums.AuditAction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "staff_logs")
@Getter
@Setter
public class StaffLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "staff_id")
    private Long staffId;

    @Enumerated(EnumType.STRING)
    private AuditAction action; // CREATE, UPDATE, DELETE

    @Column(name = "log_time")
    private LocalDateTime logTime;

    @Column(name = "log_detail", columnDefinition = "TEXT")
    private String logDetail;
}
