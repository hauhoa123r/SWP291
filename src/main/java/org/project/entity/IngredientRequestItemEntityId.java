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
public class IngredientRequestItemEntityId implements Serializable {
    private static final long serialVersionUID = -6322280859878596582L;
    @NotNull
    @Column(name = "ingredient_request_id", nullable = false)
    private Long ingredientRequestId;

    @NotNull
    @Column(name = "medicine_ingredient_id", nullable = false)
    private Long medicineIngredientId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        IngredientRequestItemEntityId entity = (IngredientRequestItemEntityId) o;
        return Objects.equals(this.medicineIngredientId, entity.medicineIngredientId) &&
                Objects.equals(this.ingredientRequestId, entity.ingredientRequestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicineIngredientId, ingredientRequestId);
    }

}