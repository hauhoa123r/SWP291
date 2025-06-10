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
public class PrescriptionItemEntityId implements Serializable {
    private static final long serialVersionUID = -1981666780714588873L;
    @NotNull
    @Column(name = "prescription_id", nullable = false)
    private Long prescriptionId;

    @NotNull
    @Column(name = "medicine_id", nullable = false)
    private Long medicineId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PrescriptionItemEntityId entity = (PrescriptionItemEntityId) o;
        return Objects.equals(this.prescriptionId, entity.prescriptionId) &&
                Objects.equals(this.medicineId, entity.medicineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prescriptionId, medicineId);
    }

}