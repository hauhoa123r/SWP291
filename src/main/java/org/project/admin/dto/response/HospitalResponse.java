package org.project.admin.dto.response;

import lombok.Data;

@Data
public class HospitalResponse {
    private Long hospitalId;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String avatarUrl;
}
