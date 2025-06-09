package org.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "PricingPlanSubscriptionEntityEntity")
@Table(name = "pricing_plan_subscriptions", schema = "swp391")
public class PricingPlanSubscriptionEntity {
    @EmbeddedId
    private PricingPlanSubscriptionEntityId id;

    @MapsId("patientId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patientEntity;

    @MapsId("pricingPlanId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pricing_plan_id", nullable = false)
    private PricingPlanEntity pricingPlanEntity;

}