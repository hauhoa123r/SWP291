package org.project.admin.controller;

import org.project.admin.dto.request.HospitalRequest;
import org.project.admin.dto.response.HospitalResponse;
import org.project.admin.service.HospitalService;
import org.project.admin.util.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
@RequiredArgsConstructor
public class HospitalAdminController {

    private final HospitalService hospitalService;

    @GetMapping
    public List<HospitalResponse> getAll() {
        return hospitalService.getAllHospitals();
    }

    @PostMapping
    public HospitalResponse create(@Valid @RequestBody HospitalRequest request) {
        return hospitalService.createHospital(request);
    }

    @PutMapping("/{id}")
    public HospitalResponse update(@PathVariable Long id,
                                   @Valid @RequestBody HospitalRequest request) {
        return hospitalService.updateHospital(id, request);
    }

    @GetMapping("/{id}")
    public HospitalResponse getById(@PathVariable Long id) {
        return hospitalService.getHospitalById(id);
    }

    @GetMapping("/paged")
    public PageResponse<HospitalResponse> getAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return hospitalService.getAllHospitals(page, size);
    }
}
