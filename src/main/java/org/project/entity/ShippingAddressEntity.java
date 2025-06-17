package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Entity
@Table(name = "shipping_addresses", schema = "swp391")
public class ShippingAddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipping_address_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Size(max = 255)
    @NotNull
    @Column(name = "province", nullable = false)
    private String province;

    @Size(max = 255)
    @NotNull
    @Column(name = "district", nullable = false)
    private String district;

    @Size(max = 255)
    @NotNull
    @Column(name = "commune", nullable = false)
    private String commune;

    @Lob
    @Column(name = "detail_address")
    private String detailAddress;
    @OneToMany
    private Set<OrderEntity> orderEntities = new LinkedHashSet<>();

/*
 TODO [Reverse Engineering] create field to map the 'address_type' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "address_type", columnDefinition = "enum not null")
    private Object addressType;
*/
}