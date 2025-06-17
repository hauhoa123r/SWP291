package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table(name = "departments", schema = "swp391")
public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", nullable = false)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private StaffEntity staffEntity;

    @NotNull
    @OneToMany(mappedBy = "departmentEntity")
    private Set<StaffEntity> staffEntities = new LinkedHashSet<>();

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Size(max = 255)
    @Column(name = "video_url")
    private String videoUrl;

    @Size(max = 255)
    @Column(name = "banner_url")
    private String bannerUrl;

    @Size(max = 255)
    @Column(name = "slogan")
    private String slogan;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hospital_id", nullable = false)
    private HospitalEntity hospital;

    @OneToMany(mappedBy = "departmentEntity")
    private Set<ServiceEntity> serviceEntities = new LinkedHashSet<>();
}