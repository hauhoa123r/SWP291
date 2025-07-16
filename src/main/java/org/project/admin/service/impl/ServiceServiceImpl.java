package org.project.admin.service.impl;

import org.project.admin.dto.request.ServiceRequest;
import org.project.admin.dto.response.ServiceResponse;
import org.project.admin.entity.Department;
import org.project.admin.mapper.ServiceMapper;
import org.project.admin.repository.DepartmentRepository;
import org.project.admin.repository.ServiceRepository;
import org.project.admin.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminServiceServiceImpl")
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;
    private final DepartmentRepository departmentRepository;
    private final ServiceMapper serviceMapper;

    @Override
    public ServiceResponse getById(Long id) {
        org.project.admin.entity.Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dịch vụ"));
        return serviceMapper.toResponse(service);
    }

    @Override
    public List<ServiceResponse> getAll() {
        return serviceMapper.toResponseList(serviceRepository.findAll());
    }

    @Override
    public ServiceResponse create(ServiceRequest req) {
        Department dept = departmentRepository.findById(req.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng ban"));
        org.project.admin.entity.Service entity = serviceMapper.toEntity(req, dept);
        entity = serviceRepository.save(entity);
        return serviceMapper.toResponse(entity);
    }
}

