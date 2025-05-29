package org.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "suppliers")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierEntity {

    @Column(name = "supplier_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name ="phone")
    private String phoneNumber;

    @OneToMany(mappedBy = "supplierEntity", fetch = FetchType.LAZY)
    private List<SupplierTransactionEntity> supplierTransactionEntities;
}
