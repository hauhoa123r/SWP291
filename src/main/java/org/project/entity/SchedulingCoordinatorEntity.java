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
@Table(name = "scheduling_coordinators", schema = "swp391")
public class SchedulingCoordinatorEntity {
    @Id
    @Column(name = "scheduling_coordinator_id", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "scheduling_coordinator_id", nullable = false)
    private StaffEntity staffEntity;

    @OneToMany
    private Set<AppointmentEntity> appointmentEntities = new LinkedHashSet<>();

/*
 TODO [Reverse Engineering] create field to map the 'scheduling_coordinator_rank' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @ColumnDefault("'ASSISTANT_COORDINATOR'")
    @Column(name = "scheduling_coordinator_rank", columnDefinition = "enum not null")
    private Object schedulingCoordinatorRank;
*/
}