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
public class SupplierTransactionItemEntityId implements Serializable {
    private static final long serialVersionUID = -574245831273568671L;
    @NotNull
    @Column(name = "supplier_transaction_id", nullable = false)
    private Long supplierTransactionId;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SupplierTransactionItemEntityId entity = (SupplierTransactionItemEntityId) o;
        return Objects.equals(this.productId, entity.productId) &&
                Objects.equals(this.supplierTransactionId, entity.supplierTransactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, supplierTransactionId);
    }

}