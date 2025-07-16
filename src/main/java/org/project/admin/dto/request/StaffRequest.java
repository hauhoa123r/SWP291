package org.project.admin.dto.request;

import org.project.admin.enums.staffs.StaffRole;
import org.project.admin.enums.staffs.StaffType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StaffRequest {
    private Long userId;
    private StaffRole staffRole;
    private Long managerId;
    private Long departmentId;
    private String fullName;
    private String avatarUrl;
    private LocalDate hireDate;

    @Min(value = 1, message = "Rank level must be between 1 and 7.")
    @Max(value = 7, message = "Rank level must be between 1 and 7.")
    private int rankLevel;
    private StaffType staffType;
    private Long hospitalId;
}
