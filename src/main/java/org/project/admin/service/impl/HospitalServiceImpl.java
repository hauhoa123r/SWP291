package org.project.admin.service.impl;

import org.project.admin.dto.request.HospitalRequest;
import org.project.admin.dto.response.HospitalResponse;
import org.project.admin.entity.Hospital;
import org.project.admin.mapper.HospitalMapper;
import org.project.admin.repository.HospitalRepository;
import org.project.admin.service.HospitalService;
import org.project.admin.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminHospitalServiceImpl")
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;
    private final HospitalMapper hospitalMapper;

    @Override
    public List<HospitalResponse> getAllHospitals() {
        List<Hospital> hospitals = hospitalRepository.findAll();
        return hospitalMapper.toResponseList(hospitals);
    }


    @Override
    public HospitalResponse createHospital(HospitalRequest request) {
        Hospital hospital = hospitalMapper.toEntity(request);
        hospital = hospitalRepository.save(hospital);
        return hospitalMapper.toResponse(hospital);
    }

    @Override
    public HospitalResponse updateHospital(Long id, HospitalRequest request) {
        Hospital existing = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh viện"));

        Hospital updated = hospitalMapper.toEntity(request);
        updated.setHospitalId(id);

        updated = hospitalRepository.save(updated);
        return hospitalMapper.toResponse(updated);
    }

    @Override
    public HospitalResponse getHospitalById(Long id) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh viện"));
        return hospitalMapper.toResponse(hospital);
    }

    @Override
    public PageResponse<HospitalResponse> getAllHospitals(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Hospital> hospitalPage = hospitalRepository.findAll(pageable);
        Page<HospitalResponse> mappedPage = hospitalPage.map(hospitalMapper::toResponse);
        return new PageResponse<>(mappedPage);
    }
}
