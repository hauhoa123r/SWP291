package org.project.admin.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StaffScheduleSearchRequest {
    private Long staffId;
    private LocalDate availableDate;
    private LocalDate availableDateFrom;
    private LocalDate availableDateTo;
    private LocalDateTime startTimeFrom;
    private LocalDateTime startTimeTo;
    private LocalDateTime endTimeFrom;
    private LocalDateTime endTimeTo;
    private String name;
    private String staffRole;
}

