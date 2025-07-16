package org.project.admin.dto.request;

import org.project.admin.enums.staffs.StaffRole;
import org.project.admin.enums.staffs.StaffType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StaffSearchRequest {
    private Long userId;
    private Long staffId;
    private String keyword;
    private StaffRole staffRole;
    private StaffType staffType;
    private Long departmentId;
    private Long hospitalId;
    private Integer rankLevel;
    private LocalDate hireDateFrom;
    private LocalDate hireDateTo;
}
