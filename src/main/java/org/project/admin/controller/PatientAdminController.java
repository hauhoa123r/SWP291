package org.project.admin.controller;

import org.project.admin.dto.request.PatientRequest;
import org.project.admin.dto.request.PatientSearchRequest;
import org.project.admin.dto.response.PatientResponse;
import org.project.admin.service.PatientService;
import org.project.admin.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/patients")
@RequiredArgsConstructor
public class PatientAdminController {
    private final PatientService patientService;

    @GetMapping
    public List<PatientResponse> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/page")
    public PageResponse<PatientResponse> getPatientPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return patientService.getPatientPage(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public PatientResponse getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @PostMapping
    public PatientResponse createPatient(@RequestBody PatientRequest req) {
        return patientService.createPatient(req);
    }

    @PutMapping("/{id}")
    public PatientResponse updatePatient(@PathVariable Long id, @RequestBody PatientRequest req) {
        return patientService.updatePatient(id, req);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }

    @PostMapping("/search")
    public PageResponse<PatientResponse> searchPatients(
            @RequestBody PatientSearchRequest req,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return patientService.searchPatientPage(req, PageRequest.of(page, size));
    }

    @GetMapping("/search-by-name")
    public List<PatientResponse> searchPatientsByName(@RequestParam String name) {
        return patientService.searchPatientsByName(name);
    }

}
