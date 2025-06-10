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
@Table(name = "staff_experiences", schema = "swp391")
public class StaffExperienceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_experience_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "staff_id", nullable = false)
    private StaffEntity staffEntity;

    @NotNull
    @Column(name = "year", nullable = false)
    private Integer year;

    @Size(max = 255)
    @NotNull
    @Column(name = "department", nullable = false)
    private String department;

    @Size(max = 255)
    @NotNull
    @Column(name = "position", nullable = false)
    private String position;

    @Size(max = 255)
    @NotNull
    @Column(name = "hospital", nullable = false)
    private String hospital;

    @Size(max = 255)
    @NotNull
    @Column(name = "result", nullable = false)
    private String result;

}