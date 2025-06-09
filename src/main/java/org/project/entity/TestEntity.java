package org.project.entity;

import jakarta.persistence.*;
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
@Entity(name = "TestEntityEntity")
@Table(name = "tests", schema = "swp391")
public class TestEntity {
    @Id
    @Column(name = "test_id", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "test_id", nullable = false)
    private ProductEntity productEntity;

    @OneToMany
    private Set<TestRequestItemEntity> testRequestItemEntities = new LinkedHashSet<>();

}