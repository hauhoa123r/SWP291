package org.project.admin.service.impl;

import org.project.admin.dto.request.DoctorRequest;
import org.project.admin.dto.response.DoctorResponse;
import org.project.admin.entity.Doctor;
import org.project.admin.entity.Staff;
import org.project.admin.mapper.DoctorMapper;
import org.project.admin.repository.DoctorRepository;
import org.project.admin.repository.StaffRepository;
import org.project.admin.service.DoctorService;
import org.project.admin.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("adminDoctorServiceImpl")
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final StaffRepository staffRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public DoctorResponse createDoctor(DoctorRequest request) {
        Staff staff = staffRepository.findById(request.getStaffId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));

        Doctor doctor = doctorMapper.toEntity(request);
        doctor = doctorRepository.save(doctor);
        return doctorMapper.toResponse(doctor, staff);
    }

    @Override
    public PageResponse<DoctorResponse> getAllDoctorsPaged(int page, int size) {
        Page<Doctor> doctorPage = doctorRepository.findAll(PageRequest.of(page, size));
        Page<DoctorResponse> mappedPage = doctorPage.map(doc -> {
            Staff staff = staffRepository.findById(doc.getDoctorId()).orElse(null);
            return doctorMapper.toResponse(doc, staff);
        });
        return new PageResponse<>(mappedPage);
    }

    @Override
    public DoctorResponse getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bác sĩ"));
        Staff staff = staffRepository.findById(doctor.getDoctorId()).orElse(null);
        return doctorMapper.toResponse(doctor, staff);
    }
    @Override
    public DoctorResponse updateDoctor(Long id, DoctorRequest request) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bác sĩ"));

        doctor.setDoctorRank(doctorMapper.mapRankLevelToEnum(request.getRankLevel()));
        doctorRepository.save(doctor);

        Staff staff = staffRepository.findById(id).orElse(null);
        return doctorMapper.toResponse(doctor, staff);
    }

    @Override
    public List<DoctorResponse> searchDoctorsByName(String name) {
        List<Doctor> doctors = doctorRepository.findByStaffNameContainingIgnoreCase(name);
        return doctors.stream().map(doc -> {
            Staff staff = staffRepository.findById(doc.getDoctorId()).orElse(null);
            return doctorMapper.toResponse(doc, staff);
        }).toList();
    }


}
