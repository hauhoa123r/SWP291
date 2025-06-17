package org.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MakeAppointmentDTO {
    private String doctorName;
    private String startDate;
    private String hospitalName;
    private String hospitalAddress;
    private String departmentName;
}
