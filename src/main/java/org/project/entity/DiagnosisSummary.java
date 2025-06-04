package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "diagnosis_summary", schema = "swp391")
public class DiagnosisSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_id", nullable = false)
    private Long id;

    @NotNull
    @Lob
    @Column(name = "summary", nullable = false)
    private String summary;

    @Column(name = "prescribed_medications")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> prescribedMedications;

}