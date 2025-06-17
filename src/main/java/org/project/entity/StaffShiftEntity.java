package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "staff_shifts", schema = "swp391")
public class StaffShiftEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_shift_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "staff_id", nullable = false)
    private StaffEntity staffEntity;

    @NotNull
    @Column(name = "date", nullable = false)
    private Date date;

/*
 TODO [Reverse Engineering] create field to map the 'shift_type' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "shift_type", columnDefinition = "enum not null")
    private Object shiftType;
*/
}