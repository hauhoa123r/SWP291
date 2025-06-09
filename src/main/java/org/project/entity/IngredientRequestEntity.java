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
@Entity(name = "IngredientRequestEntityEntity")
@Table(name = "ingredient_requests", schema = "swp391")
public class IngredientRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_request_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "appointment_id", nullable = false)
    private AppointmentEntity appointmentEntity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pharmacist_id", nullable = false)
    private PharmacistEntity pharmacistEntity;

    @NotNull
    @Lob
    @Column(name = "reason", nullable = false)
    private String reason;

    @ManyToMany(mappedBy = "ingredientRequestEntities")
    private Set<MedicineIngredientEntity> medicineIngredientEntities = new LinkedHashSet<>();

    @OneToMany
    private Set<PrescriptionEntity> prescriptionEntities = new LinkedHashSet<>();

}