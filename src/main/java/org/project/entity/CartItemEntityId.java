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
public class CartItemEntityId implements Serializable {
    private static final long serialVersionUID = -2317679126036524155L;
    @NotNull
    @Column(name = "cart_item_id", nullable = false)
    private Long cartItemId;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CartItemEntityId entity = (CartItemEntityId) o;
        return Objects.equals(this.userId, entity.userId) &&
                Objects.equals(this.cartItemId, entity.cartItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, cartItemId);
    }

}