package org.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pharmacy_staff")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyStaffEntity {

    @Column(name = "staff_id", nullable = false)
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "pharmacy_role_notes")
    private String pharmacyRoleNotes;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

    @OneToMany(mappedBy = "pharmacyStaffEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SupplierTransactionEntity> supplierTransactionEntities;
}
