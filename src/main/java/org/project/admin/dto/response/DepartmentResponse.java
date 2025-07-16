package org.project.admin.dto.response;

import lombok.Data;

@Data
public class DepartmentResponse {
    private Long departmentId;
    private String name;
    private String description;
    private String videoUrl;
    private String bannerUrl;
    private String slogan;
//    private Long managerId;
    private long completedAppointments;
    private long doctorCount;
}
