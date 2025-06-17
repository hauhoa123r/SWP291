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
@Table(name = "medicine_ingredients", schema = "swp391")
public class MedicineIngredientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicine_ingredient_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medicine_id", nullable = false)
    private MedicineEntity medicineEntity;

    @Size(max = 255)
    @NotNull
    @Column(name = "chemical_name", nullable = false)
    private String chemicalName;

    @Size(max = 255)
    @NotNull
    @Column(name = "generic_name", nullable = false)
    private String genericName;

    @Lob
    @Column(name = "description")
    private String description;

    @Size(max = 255)
    @NotNull
    @Column(name = "therapeutic_class", nullable = false)
    private String therapeuticClass;

    @Size(max = 255)
    @NotNull
    @Column(name = "atc_code", nullable = false)
    private String atcCode;

    @Size(max = 255)
    @NotNull
    @Column(name = "strength", nullable = false)
    private String strength;

    @OneToMany
    private Set<IngredientInteractionEntity> ingredientInteractionEntities1 = new LinkedHashSet<>();

    @OneToMany
    private Set<IngredientInteractionEntity> ingredientInteractionEntities2 = new LinkedHashSet<>();

    @ManyToMany
    private Set<IngredientRequestEntity> ingredientRequestEntities = new LinkedHashSet<>();

}