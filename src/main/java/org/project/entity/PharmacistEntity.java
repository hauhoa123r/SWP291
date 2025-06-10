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
@Table(name = "pharmacists", schema = "swp391")
public class PharmacistEntity {
    @Id
    @Column(name = "pharmacist_id", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pharmacist_id", nullable = false)
    private StaffEntity staffEntity;

    @OneToMany
    private Set<IngredientRequestEntity> ingredientRequestEntities = new LinkedHashSet<>();

/*
 TODO [Reverse Engineering] create field to map the 'pharmacist_rank' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @ColumnDefault("'PHARMACY_INTERN'")
    @Column(name = "pharmacist_rank", columnDefinition = "enum not null")
    private Object pharmacistRank;
*/
}