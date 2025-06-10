package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "staff_schedules", schema = "swp391")
public class StaffScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_schedule_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "staff_id", nullable = false)
    private StaffEntity staffEntity;

    @NotNull
    @Column(name = "available_date", nullable = false)
    private Date availableDate;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private Timestamp startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private Timestamp endTime;

}