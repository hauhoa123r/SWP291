package org.project.admin.controller;

import org.project.admin.dto.request.StaffRequest;
import org.project.admin.dto.request.StaffSearchRequest;
import org.project.admin.dto.response.SchedulingCoordinatorResponse;
import org.project.admin.dto.response.StaffResponse;
import org.project.admin.service.StaffService;
import org.project.admin.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staffs")
@RequiredArgsConstructor
public class StaffAdminController {

    private final StaffService staffService;

    @PostMapping
    public StaffResponse create(@RequestBody StaffRequest request) {
        return staffService.createStaff(request);
    }

    @GetMapping("/paged")
    public PageResponse<StaffResponse> getPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return staffService.getAllStaffPaged(page, size);
    }

    @GetMapping("/{id}")
    public StaffResponse getById(@PathVariable Long id) {
        return staffService.getStaffById(id);
    }


    @PostMapping("/search")
    public PageResponse<StaffResponse> search(
            @RequestBody StaffSearchRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return staffService.search(request, page, size);
    }

    @PutMapping("/{id}")
    public StaffResponse update(@PathVariable Long id, @RequestBody StaffRequest request) {
        return staffService.updateStaff(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        staffService.deleteStaff(id);
    }

    @GetMapping("/search-coordinator")
    public List<SchedulingCoordinatorResponse> searchCoordinators(
            @RequestParam(required = false) String name
    ) {
        return staffService.getSchedulingCoordinators(name);
    }

    @GetMapping("/search-by-name")
    public List<StaffResponse> searchByName(@RequestParam String name) {
        return staffService.searchStaffsByName(name);
    }
}
