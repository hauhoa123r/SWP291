package org.project.admin.service;

import org.project.admin.dto.request.ServiceRequest;
import org.project.admin.dto.response.ServiceResponse;

import java.util.List;

public interface ServiceService {
    ServiceResponse getById(Long id);
    List<ServiceResponse> getAll();
    ServiceResponse create(ServiceRequest req);
}

