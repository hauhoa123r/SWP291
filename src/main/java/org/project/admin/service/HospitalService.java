package org.project.admin.service;

import org.project.admin.dto.request.HospitalRequest;
import org.project.admin.dto.response.HospitalResponse;
import org.project.admin.util.PageResponse;

import java.util.List;

public interface HospitalService {
    List<HospitalResponse> getAllHospitals();

    HospitalResponse createHospital(HospitalRequest request);

    HospitalResponse updateHospital(Long id, HospitalRequest request);

    HospitalResponse getHospitalById(Long id);

    PageResponse<HospitalResponse> getAllHospitals(int page, int size);
}
