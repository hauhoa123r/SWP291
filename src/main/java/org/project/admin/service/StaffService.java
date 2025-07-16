package org.project.admin.service;

import org.project.admin.dto.request.StaffRequest;
import org.project.admin.dto.request.StaffSearchRequest;
import org.project.admin.dto.response.SchedulingCoordinatorResponse;
import org.project.admin.dto.response.StaffResponse;
import org.project.admin.util.PageResponse;

import java.util.List;


public interface StaffService {
    StaffResponse createStaff(StaffRequest request);
    PageResponse<StaffResponse> getAllStaffPaged(int page, int size);
    StaffResponse getStaffById(Long id);
    PageResponse<StaffResponse> search(StaffSearchRequest request, int page, int size);
    StaffResponse updateStaff(Long id, StaffRequest request);
    void deleteStaff(Long id);


    List<SchedulingCoordinatorResponse> getSchedulingCoordinators(String name);

    List<StaffResponse> searchStaffsByName(String name);
}
