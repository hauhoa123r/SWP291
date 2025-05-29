package org.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pharmacy_inventory")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyInventoryEntity {

    @Column(name = "inventory_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "current_stock", nullable = false)
    private Long currentStock;

    @Column(name = "minimum_stock", nullable = false)
    private Long mininumStock;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private PharmacyProductEntity pharmacyProductEntity;
}
