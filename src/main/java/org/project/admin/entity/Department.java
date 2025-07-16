package org.project.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "departments")
@Getter
@Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long departmentId;

//    @OneToOne
//    @JoinColumn(name = "manager_id", unique = true, nullable = false)
//    private Staff manager;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "banner_url")
    private String bannerUrl;

    @Column(name = "slogan")
    private String slogan;
}
