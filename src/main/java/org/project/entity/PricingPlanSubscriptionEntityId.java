package org.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class PricingPlanSubscriptionEntityId implements Serializable {
    private static final long serialVersionUID = -2844011976011047141L;
    @NotNull
    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @NotNull
    @Column(name = "pricing_plan_id", nullable = false)
    private Long pricingPlanId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PricingPlanSubscriptionEntityId entity = (PricingPlanSubscriptionEntityId) o;
        return Objects.equals(this.pricingPlanId, entity.pricingPlanId) &&
                Objects.equals(this.patientId, entity.patientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pricingPlanId, patientId);
    }

}