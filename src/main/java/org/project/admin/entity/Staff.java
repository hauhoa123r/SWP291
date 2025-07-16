package org.project.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.project.admin.enums.staffs.StaffRole;
import org.project.admin.enums.staffs.StaffType;

import java.time.LocalDate;

@Entity
@Table(name = "staffs")
@Getter
@Setter
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Long staffId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "staff_role", nullable = false)
    private StaffRole staffRole = StaffRole.DOCTOR;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Staff manager;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Column(name = "rank_level", nullable = false)
    private int rankLevel = 1;

    @Enumerated(EnumType.STRING)
    @Column(name = "staff_type", nullable = false)
    private StaffType staffType;

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;
}
