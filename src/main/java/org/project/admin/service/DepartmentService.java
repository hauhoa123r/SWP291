package org.project.admin.service;

import org.project.admin.dto.request.DepartmentRequest;
import org.project.admin.dto.response.DepartmentResponse;
import org.project.admin.util.PageResponse;

import java.util.List;

public interface DepartmentService {
    DepartmentResponse createDepartment(DepartmentRequest request);

    DepartmentResponse updateDepartment(Long id, DepartmentRequest request);

    DepartmentResponse getDepartmentById(Long id);

    List<DepartmentResponse> findByHospitalId(Long hospitalId);

    PageResponse<DepartmentResponse> getDepartmentsWithStatsPaged(int page, int size, Long hospitalId);

    void deleteDepartment(Long id);
}
