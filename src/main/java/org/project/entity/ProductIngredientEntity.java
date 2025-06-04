package org.project.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_ingredients")
public class ProductIngredientEntity {


    @Column(name = "product_ingredient_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "strength", nullable = false)
    private String strength;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private ActiveIngredientEntity activeIngredient;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private PharmacyProductEntity pharmacyProductEntity;
}
