package org.project.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "drug_interactions")
public class DrugInteractionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "interaction_id", nullable  = false)
    private Long id;

    @Column(name = "severity", nullable = false)
    private String serverity;

    @Column(name = "desciption", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "ingredient_id_1")
    private ActiveIngredientEntity activeIngredient1;

    @ManyToOne
    @JoinColumn(name = "ingredient_id_2")
    private ActiveIngredientEntity activeIngredient2;
}
