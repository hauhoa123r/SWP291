package org.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffDTO {
    private String fullName;
    private String departmentEntityName;
    private String hospitalEntityName;
    private Integer reviewCount;
    private Double averageRating;
}
