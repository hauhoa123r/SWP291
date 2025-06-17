package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "prescription_items", schema = "swp391")
public class PrescriptionItemEntity {
    @EmbeddedId
    private PrescriptionItemEntityId id;

    @MapsId("prescriptionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prescription_id", nullable = false)
    private PrescriptionEntity prescriptionEntity;

    @MapsId("medicineId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medicine_id", nullable = false)
    private MedicineEntity medicineEntity;

    @NotNull
    @Lob
    @Column(name = "dosage", nullable = false)
    private String dosage;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "duration_days", nullable = false)
    private Integer durationDays;

}