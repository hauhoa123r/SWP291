package org.project.service;

import org.project.model.response.StaffResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StaffService {
    Page<StaffResponse> getDoctorsByPage(int index, int size);

    Page<StaffResponse> getDoctorsByDepartmentNameAndPage(String departmentName, int index, int size);

    List<StaffResponse> getColleagueDoctorByStaffId(String departmentName, Long staffId);

    boolean isDoctorExist(Long staffId);

    StaffResponse getDoctorByStaffId(Long staffId);
}
