package org.project.admin.controller;

import org.project.admin.dto.request.DoctorRequest;
import org.project.admin.dto.response.DoctorResponse;
import org.project.admin.service.DoctorService;
import org.project.admin.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorAdminController {

    private final DoctorService doctorService;

    @PostMapping
    public DoctorResponse create(@RequestBody DoctorRequest request) {
        return doctorService.createDoctor(request);
    }

    @GetMapping("/paged")
    public PageResponse<DoctorResponse> getPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return doctorService.getAllDoctorsPaged(page, size);
    }

    @GetMapping("/{id}")
    public DoctorResponse getById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    @PutMapping("/{id}")
    public DoctorResponse update(@PathVariable Long id, @RequestBody DoctorRequest request) {
        return doctorService.updateDoctor(id, request);
    }

    @GetMapping("/search")
    public List<DoctorResponse> searchDoctorsByName(@RequestParam String name) {
        return doctorService.searchDoctorsByName(name);
    }

}
