package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "lab_test_results", schema = "swp391")
public class LabTestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "test_id", nullable = false)
    private LabTest test;

    @NotNull
    @Column(name = "result_data", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> resultData;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "analyzed_at")
    private Timestamp analyzedAt;

}