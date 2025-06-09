package org.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "InventoryManagerEntityEntity")
@Table(name = "inventory_managers", schema = "swp391")
public class InventoryManagerEntity {
    @Id
    @Column(name = "inventory_manager_id", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inventory_manager_id", nullable = false)
    private StaffEntity staffEntity;

    @OneToMany
    private Set<SupplierTransactionsEntity> supplierTransactionEntities = new LinkedHashSet<>();

/*
 TODO [Reverse Engineering] create field to map the 'inventory_manager_rank' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @ColumnDefault("'INVENTORY_ASSISTANT'")
    @Column(name = "inventory_manager_rank", columnDefinition = "enum not null")
    private Object inventoryManagerRank;
*/
}