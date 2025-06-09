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
@Entity(name = "MedicalProductEntityEntity")
@Table(name = "medical_products", schema = "swp391")
public class MedicalProductEntity {
    @Id
    @Column(name = "medical_product_id", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medical_product_id", nullable = false)
    private ProductEntity productEntities;

}