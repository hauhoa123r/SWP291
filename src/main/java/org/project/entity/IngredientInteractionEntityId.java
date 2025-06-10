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
public class IngredientInteractionEntityId implements Serializable {
    private static final long serialVersionUID = -9124250868132058156L;
    @NotNull
    @Column(name = "medicine_ingredient_id_1", nullable = false)
    private Long medicineIngredientId1;

    @NotNull
    @Column(name = "medicine_ingredient_id_2", nullable = false)
    private Long medicineIngredientId2;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        IngredientInteractionEntityId entity = (IngredientInteractionEntityId) o;
        return Objects.equals(this.medicineIngredientId2, entity.medicineIngredientId2) &&
                Objects.equals(this.medicineIngredientId1, entity.medicineIngredientId1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicineIngredientId2, medicineIngredientId1);
    }

}