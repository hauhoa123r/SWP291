package org.project.admin.service;

import org.project.admin.dto.request.DoctorRequest;
import org.project.admin.dto.response.DoctorResponse;
import org.project.admin.util.PageResponse;

import java.util.List;

public interface DoctorService {
    DoctorResponse createDoctor(DoctorRequest request);
    PageResponse<DoctorResponse> getAllDoctorsPaged(int page, int size);
    DoctorResponse getDoctorById(Long id);
    DoctorResponse updateDoctor(Long id, DoctorRequest request);

    List<DoctorResponse> searchDoctorsByName(String name);
}
