package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "reviews", schema = "swp391")
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patientEntity;

    @NotNull
    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @Column(name = "rating", nullable = false)
    private Integer rating;

    @ManyToMany(mappedBy = "reviewEntities")
    private Set<ProductEntity> productEntities = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "reviewEntities")
    private Set<StaffEntity> staffEntities = new LinkedHashSet<>();

/*
 TODO [Reverse Engineering] create field to map the 'review_type' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @ColumnDefault("'STAFF'")
    @Column(name = "review_type", columnDefinition = "enum not null")
    private Object reviewType;
*/
}