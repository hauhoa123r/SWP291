package org.project.admin.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.project.admin.enums.doctors.DoctorRank;

@Entity
@Table(name = "doctors")
@Getter
@Setter
public class Doctor {
    @Id
    @Column(name = "doctor_id")
    private Long doctorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "doctor_rank", nullable = false)
    private DoctorRank doctorRank = DoctorRank.INTERN;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", referencedColumnName = "staff_id", insertable = false, updatable = false)
    private Staff staff;
}
