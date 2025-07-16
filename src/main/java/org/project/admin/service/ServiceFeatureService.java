package org.project.admin.service;

import org.project.admin.dto.request.ServiceFeatureRequest;
import org.project.admin.dto.response.ServiceFeatureResponse;

import java.util.List;

public interface ServiceFeatureService {
    List<ServiceFeatureResponse> getByServiceId(Long serviceId);
    ServiceFeatureResponse create(ServiceFeatureRequest req);
}
