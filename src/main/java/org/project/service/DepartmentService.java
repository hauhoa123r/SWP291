package org.project.service;

import org.project.model.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {
    List<DepartmentResponse> getAll();

    List<DepartmentResponse> getAllDepartmentsHaveDoctor();

    boolean isDepartmentNameExist(String departmentName);
}
