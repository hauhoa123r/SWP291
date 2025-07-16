package org.project.admin.controller;

import org.project.admin.dto.request.StaffScheduleRequest;
import org.project.admin.dto.request.StaffScheduleSearchRequest;
import org.project.admin.dto.response.StaffScheduleResponse;
import org.project.admin.service.StaffScheduleService;
import org.project.admin.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff-schedules")
@RequiredArgsConstructor
public class StaffScheduleAdminController {

    private final StaffScheduleService staffScheduleService;

    @PostMapping
    public StaffScheduleResponse create(@RequestBody StaffScheduleRequest request) {
        return staffScheduleService.create(request);
    }

    @PutMapping("/{id}")
    public StaffScheduleResponse update(@PathVariable Long id, @RequestBody StaffScheduleRequest request) {
        return staffScheduleService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        staffScheduleService.delete(id);
    }

    @GetMapping("/{id}")
    public StaffScheduleResponse getById(@PathVariable Long id) {
        return staffScheduleService.getById(id);
    }

    // Không phân trang
    @GetMapping("/by-staff/{staffId}/all")
    public List<StaffScheduleResponse> getByStaffIdAll(@PathVariable Long staffId) {
        return staffScheduleService.getByStaffId(staffId);
    }

    // Phân trang theo staffId
    @GetMapping("/by-staff/{staffId}")
    public PageResponse<StaffScheduleResponse> getByStaffId(
            @PathVariable Long staffId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return staffScheduleService.getByStaffId(staffId, pageable);
    }

    // Lấy tất cả, có phân trang
    @GetMapping
    public PageResponse<StaffScheduleResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return staffScheduleService.getAll(pageable);
    }

    //Tìm kiếm
    @PostMapping("/search")
    public PageResponse<StaffScheduleResponse> search(@RequestBody StaffScheduleSearchRequest request, Pageable pageable) {
        return staffScheduleService.search(request, pageable);
    }

}
