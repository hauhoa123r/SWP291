package org.project.entity;

import jakarta.persistence.*;
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
@Table(name = "doctors", schema = "swp391")
public class DoctorEntity {
    @Id
    @Column(name = "doctor_id", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private StaffEntity staffEntity;

    @OneToMany
    private Set<AppointmentEntity> appointmentEntities = new LinkedHashSet<>();

/*
 TODO [Reverse Engineering] create field to map the 'doctor_rank' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @ColumnDefault("'INTERN'")
    @Column(name = "doctor_rank", columnDefinition = "enum not null")
    private Object doctorRank;
*/
}