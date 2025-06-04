package org.project.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "active_ingredients")
public class ActiveIngredientEntity {

    @Column(name = "ingredients_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chemical_name", nullable = false)
    private String chemicalName;

    @Column(name = "generic_name", nullable = false)
    private String genericName;

    @Column(name = "description")
    private String description;

    @Column(name = "therapeutic_class")
    private String therapeuticClass;

    @Column(name = "atc_code")
    private String atcCode;

    @OneToMany(mappedBy = "activeIngredient1", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<DrugInteractionEntity> drugInteraction1;

    @OneToMany(mappedBy = "activeIngredient2", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<DrugInteractionEntity> drugInteraction2;

    @OneToMany(mappedBy = "activeIngredient", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<ProductIngredientEntity> productIngredients;


}
