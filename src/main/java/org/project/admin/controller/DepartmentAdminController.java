package org.project.admin.controller;

import org.project.admin.dto.request.DepartmentRequest;
import org.project.admin.dto.response.DepartmentResponse;
import org.project.admin.service.DepartmentService;
import org.project.admin.util.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentAdminController {

    private final DepartmentService departmentService;

    @PostMapping
    public DepartmentResponse create(@Valid @RequestBody DepartmentRequest request) {
        return departmentService.createDepartment(request);
    }

    @PutMapping("/{id}")
    public DepartmentResponse update(@PathVariable Long id, @Valid @RequestBody DepartmentRequest request) {
        return departmentService.updateDepartment(id, request);
    }

    @GetMapping("/{id}")
    public DepartmentResponse getById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    @GetMapping
    public List<DepartmentResponse> getDepartmentsByHospital(@RequestParam Long hospitalId) {
        return departmentService.findByHospitalId(hospitalId);
    }

    @GetMapping("/paged")
    public PageResponse<DepartmentResponse> getPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long hospitalId
    ) {
        return departmentService.getDepartmentsWithStatsPaged(page, size, hospitalId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }
}
