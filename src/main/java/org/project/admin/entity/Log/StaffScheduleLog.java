package org.project.admin.entity.Log;

import org.project.admin.enums.AuditAction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "staff_schedule_logs")
@Getter @Setter
public class StaffScheduleLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "staff_schedule_id")
    private Long staffScheduleId;

    @Enumerated(EnumType.STRING)
    private AuditAction action;

    @Column(name = "log_time")
    private LocalDateTime logTime;

    @Column(name = "log_detail", columnDefinition = "TEXT")
    private String logDetail;
}
