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
@Entity(name = "PricingPlanEntityEntity")
@Table(name = "pricing_plans", schema = "swp391")
public class PricingPlanEntity {
    @Id
    @Column(name = "pricing_plan_id", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pricing_plan_id", nullable = false)
    private ProductEntity productEntity;

    @OneToMany
    private Set<PricingPlanFeatureEntity> pricingPlanFeatureEntities = new LinkedHashSet<>();
    @ManyToMany(mappedBy = "pricingPlanEntities")
    private Set<PatientEntity> patientEntities = new LinkedHashSet<>();

/*
 TODO [Reverse Engineering] create field to map the 'plan_type' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "plan_type", columnDefinition = "enum not null")
    private Object planType;
*/
}