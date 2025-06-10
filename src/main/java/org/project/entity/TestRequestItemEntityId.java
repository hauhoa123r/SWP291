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
public class TestRequestItemEntityId implements Serializable {
    private static final long serialVersionUID = 1051005314168685175L;
    @NotNull
    @Column(name = "test_id", nullable = false)
    private Long testId;

    @NotNull
    @Column(name = "test_request_id", nullable = false)
    private Long testRequestId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TestRequestItemEntityId entity = (TestRequestItemEntityId) o;
        return Objects.equals(this.testId, entity.testId) &&
                Objects.equals(this.testRequestId, entity.testRequestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testId, testRequestId);
    }

}