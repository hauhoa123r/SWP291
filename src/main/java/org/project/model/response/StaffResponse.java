package org.project.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffResponse {
    private Long id;
    private String fullName;
    private String avatarUrl;
    private String departmentEntityName;
    private List<StaffEducationResponse> staffEducationEntities;
    private List<StaffExperienceResponse> staffExperienceEntities;
    private List<StaffSkillResponse> staffSkillEntities;
    private List<StaffQualificationResponse> staffQualificationEntities;
    private List<StaffSpecialityResponse> staffSpecialityEntities;
    private String hospitalEntityName;
    private int reviewCount;
    private double averageRating;
}
