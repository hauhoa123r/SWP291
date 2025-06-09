package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "staff_educations", schema = "swp391")
public class StaffEducationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_education_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "staff_id", nullable = false)
    private StaffEntity staff;

    @NotNull
    @Column(name = "year", nullable = false)
    private Integer year;

    @Size(max = 255)
    @NotNull
    @Column(name = "degree", nullable = false)
    private String degree;

    @Size(max = 255)
    @NotNull
    @Column(name = "institute", nullable = false)
    private String institute;

    @Size(max = 255)
    @NotNull
    @Column(name = "result", nullable = false)
    private String result;

}