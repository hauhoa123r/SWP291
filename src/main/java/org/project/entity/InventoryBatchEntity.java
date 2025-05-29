package org.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "inventory_batches")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryBatchEntity {
    @Column(name = "batch_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    @Column(name = "warehouse_location", nullable = false)
    private String wareHouseLocation;

    @Column(name = "received_at", nullable = false)
    private Date receivedDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private PharmacyProductEntity pharmacyProductEntity;
}
