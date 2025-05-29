package org.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_feedbacks")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductFeedbackEntity {

    @Column(name = "feedback_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rating")
    private Long rating;

    @Column(name = "comment")
    private String commnet;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private PharmacyProductEntity pharmacyProductEntity;
}
