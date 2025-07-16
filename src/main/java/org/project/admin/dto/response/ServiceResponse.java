package org.project.admin.dto.response;

import lombok.Data;

import java.util.List;

// ServiceResponse.java
@Data
public class ServiceResponse {
    private Long serviceId;
    private Long departmentId;
    private List<ServiceFeatureResponse> features;
}

