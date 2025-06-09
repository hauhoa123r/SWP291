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
@Entity(name = "IngredientInteractionEntityEntity")
@Table(name = "ingredient_interactions", schema = "swp391")
public class IngredientInteractionEntity {
    @EmbeddedId
    private IngredientInteractionEntityId id;

    @MapsId("medicineIngredientId1")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medicine_ingredient_id_1", nullable = false)
    private MedicineIngredientEntity medicineIngredientEntity1;

    @MapsId("medicineIngredientId2")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medicine_ingredient_id_2", nullable = false)
    private MedicineIngredientEntity medicineIngredientEntity2;

    @Lob
    @Column(name = "description")
    private String description;

/*
 TODO [Reverse Engineering] create field to map the 'severity' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "severity", columnDefinition = "enum not null")
    private Object severity;
*/
}