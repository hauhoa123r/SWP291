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
@Entity
@Table(name = "ingredient_request_items", schema = "swp391")
public class IngredientRequestItemEntity {
    @EmbeddedId
    private IngredientRequestItemEntityId id;

    @MapsId("ingredientRequestId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ingredient_request_id", nullable = false)
    private IngredientRequestEntity ingredientRequestEntity;

    @MapsId("medicineIngredientId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medicine_ingredient_id", nullable = false)
    private MedicineIngredientEntity medicineIngredientEntity;

}