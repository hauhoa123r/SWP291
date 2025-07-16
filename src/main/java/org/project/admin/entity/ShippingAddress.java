package org.project.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.project.admin.enums.address.AddressType;

@Entity
@Table(name = "shipping_addresses")
@Getter
@Setter
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipping_address_id")
    private Long shippingAddressId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type")
    private AddressType addressType;

    @Column(name = "province")
    private String province;

    @Column(name = "district")
    private String district;

    @Column(name = "commune")
    private String commune;

    @Column(name = "detail_address")
    private String detailAddress;
}

