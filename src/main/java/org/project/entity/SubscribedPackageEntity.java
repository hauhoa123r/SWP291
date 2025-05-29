package org.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "subscribed_packages")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubscribedPackageEntity {

    @Column(name = "subsciption_id")
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "start_date")
    private Date startDate;

    @Column (name = "end_date")
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private PharmacyProductEntity pharmacyProductEntity;
}
