package org.project.admin.dto.response;

import org.project.admin.enums.doctors.DoctorRank;
import lombok.Data;
@Data
public class DoctorResponse {
    private Long doctorId;
    private DoctorRank doctorRank;
    private String fullName;
    private String avatarUrl;
}
