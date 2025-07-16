package org.project.admin.dto.response;
import org.project.admin.enums.staffs.StaffRole;
import org.project.admin.enums.staffs.StaffType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StaffResponse {
    private Long userId;
    private Long staffId;
    private String fullName;
    private String avatarUrl;
    private StaffRole staffRole;
    private StaffType staffType;
    private int rankLevel;
    private LocalDate hireDate;
    private DepartmentResponse department;
    private HospitalResponse hospital;
}
