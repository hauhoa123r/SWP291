package org.project.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "service_features")
@Getter
@Setter
public class ServiceFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_feature_id")
    private Long serviceFeatureId;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}

