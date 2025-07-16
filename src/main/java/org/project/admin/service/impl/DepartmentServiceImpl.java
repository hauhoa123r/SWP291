package org.project.admin.service.impl;

import org.project.admin.dto.request.DepartmentRequest;
import org.project.admin.dto.response.DepartmentResponse;
import org.project.admin.entity.Department;
import org.project.admin.mapper.DepartmentMapper;
import org.project.admin.repository.DepartmentRepository;
import org.project.admin.service.DepartmentService;
import org.project.admin.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("adminDepartmentServiceImpl")
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest request) {
        Department department = departmentMapper.toEntity(request);
        department = departmentRepository.save(department);
        return departmentMapper.toResponse(department);
    }

    @Override
    public DepartmentResponse updateDepartment(Long id, DepartmentRequest request) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng ban"));

        Department updated = departmentMapper.toEntity(request);
        updated.setDepartmentId(existing.getDepartmentId());

        departmentRepository.save(updated);
        return departmentMapper.toResponse(updated);
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng ban"));
        return departmentMapper.toResponse(dept);
    }

    @Override
    public List<DepartmentResponse> findByHospitalId(Long hospitalId) {
        List<Department> departments = departmentRepository.findByHospitalId(hospitalId);
        return departments.stream()
                .map(departmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PageResponse<DepartmentResponse> getDepartmentsWithStatsPaged(int page, int size, Long hospitalId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Department> departmentPage;

        if (hospitalId != null) {
            departmentPage = departmentRepository.findByHospitalId(hospitalId, pageable);
        } else {
            departmentPage = departmentRepository.findAll(pageable);
        }

        List<DepartmentRepository.DepartmentStats> statsList = departmentRepository.getDepartmentStatsByHospital(hospitalId);
        Map<Long, DepartmentRepository.DepartmentStats> statsMap = statsList.stream()
                .collect(Collectors.toMap(DepartmentRepository.DepartmentStats::getDepartmentId, stat -> stat));

        Page<DepartmentResponse> mappedPage = departmentPage.map(department -> {
            DepartmentResponse response = departmentMapper.toResponse(department);
            DepartmentRepository.DepartmentStats stat = statsMap.get(department.getDepartmentId());
            if (stat != null) {
                response.setCompletedAppointments(stat.getCompletedAppointments());
                response.setDoctorCount(stat.getDoctorCount());
            }
            return response;
        });

        return new PageResponse<>(mappedPage);
    }

    @Override
    public void deleteDepartment(Long id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng ban"));
        departmentRepository.delete(dept);
    }
}
