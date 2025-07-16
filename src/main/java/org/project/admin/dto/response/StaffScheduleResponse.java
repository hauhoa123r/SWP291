package org.project.admin.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StaffScheduleResponse {
    private Long staffScheduleId;
    private Long staffId;
    private String staffFullName;
    private String staffAvatarUrl;
    private String staffRole;
    private LocalDate availableDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
