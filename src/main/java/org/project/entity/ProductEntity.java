package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "ProductEntityEntity")
@Table(name = "products", schema = "swp391")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Size(max = 255)
    @NotNull
    @Column(name = "unit", nullable = false)
    private String unit;

    @NotNull
    @Column(name = "stock_quantities", nullable = false)
    private Integer stockQuantities;

    @Size(max = 255)
    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany
    private Set<CartItemEntity> cartItemEntities = new LinkedHashSet<>();

    @OneToOne
    private MedicalProductEntity medicalProductEntity;

    @OneToOne
    private MedicineEntity medicineEntity;

    @OneToMany
    private Set<OrderItemEntity> orderItemEntities = new LinkedHashSet<>();

    @OneToOne
    private PricingPlanEntity pricingPlanEntity;

    @OneToMany
    private Set<ProductAdditionalInfoEntity> productAdditionalInfoEntities = new LinkedHashSet<>();

    @ManyToMany
    private Set<CategoryEntity> categoryEntities = new LinkedHashSet<>();

    @ManyToMany
    private Set<ReviewEntity> reviewEntities = new LinkedHashSet<>();
    @OneToMany
    private Set<SupplierTransactionItemEntity> supplierTransactionItemEntities = new LinkedHashSet<>();

    @OneToMany
    private Set<ProductTagEntity> productTagEntities = new LinkedHashSet<>();
    @OneToOne
    private TestEntity testEntity;

    @ManyToMany
    private Set<DepartmentEntity> departmentEntities = new LinkedHashSet<>();
    @ManyToMany
    private Set<UserEntity> userEntities = new LinkedHashSet<>();

/*
 TODO [Reverse Engineering] create field to map the 'product_type' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @ColumnDefault("'MEDICAL_PRODUCT'")
    @Column(name = "product_type", columnDefinition = "enum not null")
    private Object productType;
*/
/*
 TODO [Reverse Engineering] create field to map the 'product_status' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @ColumnDefault("'ACTIVE'")
    @Column(name = "product_status", columnDefinition = "enum not null")
    private Object productStatus;
*/
/*
 TODO [Reverse Engineering] create field to map the 'label' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @ColumnDefault("'STANDARD'")
    @Column(name = "label", columnDefinition = "enum not null")
    private Object label;
*/
}