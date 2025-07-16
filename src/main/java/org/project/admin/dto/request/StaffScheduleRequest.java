package org.project.admin.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StaffScheduleRequest {
    private Long staffId;
    private LocalDate availableDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
