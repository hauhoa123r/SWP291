package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.project.enums.Gender;
import org.project.enums.StaffStatus;

import java.sql.Timestamp;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "medical_staff", schema = "swp391")
public class MedicalStaffEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id", nullable = false)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    @Size(max = 255)
    @NotNull
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Size(max = 255)
    @Column(name = "license_number")
    private String licenseNumber;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender  gender;

/*
 TODO [Reverse Engineering] create field to map the 'gender' column
 Available actions: Uncomment as is | Remove column mapping
    @Lob
    @Column(name = "gender")
    private String gender;
*/
/*
 TODO [Reverse Engineering] create field to map the 'employment_type' column
 Available actions: Uncomment as is | Remove column mapping
    @ColumnDefault("'full_time'")
    @Lob
    @Column(name = "employment_type")
    private String employmentType;
*/

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StaffStatus staffStatus;

/*
 TODO [Reverse Engineering] create field to map the 'status' column
 Available actions: Uncomment as is | Remove column mapping
    @ColumnDefault("'active'")
    @Lob
    @Column(name = "status")
    private String status;
*/
}