package org.project.admin.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class ServiceRequest {
    @NotNull
    private Long departmentId;
    // Nếu muốn thêm nhiều features khi tạo service thì thêm:
    // private List<ServiceFeatureRequest> features;
}

