package org.project.admin.service;

import org.project.admin.dto.request.StaffScheduleRequest;
import org.project.admin.dto.request.StaffScheduleSearchRequest;
import org.project.admin.dto.response.StaffScheduleResponse;
import org.project.admin.util.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StaffScheduleService {
    StaffScheduleResponse create(StaffScheduleRequest request);
    StaffScheduleResponse update(Long id, StaffScheduleRequest request);
    void delete(Long id);
    StaffScheduleResponse getById(Long id);
    List<StaffScheduleResponse> getByStaffId(Long staffId);
    PageResponse<StaffScheduleResponse> getByStaffId(Long staffId, Pageable pageable);
    PageResponse<StaffScheduleResponse> getAll(Pageable pageable);
    PageResponse<StaffScheduleResponse> search(StaffScheduleSearchRequest request, Pageable pageable);
}
