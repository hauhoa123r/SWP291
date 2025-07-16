package org.project.admin.service.impl;

import org.project.admin.dto.request.ServiceFeatureRequest;
import org.project.admin.dto.response.ServiceFeatureResponse;
import org.project.admin.entity.ServiceFeature;
import org.project.admin.mapper.ServiceFeatureMapper;
import org.project.admin.repository.ServiceFeatureRepository;
import org.project.admin.repository.ServiceRepository;
import org.project.admin.service.ServiceFeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminServiceFeatureServiceImpl")
@RequiredArgsConstructor
public class ServiceFeatureServiceImpl implements ServiceFeatureService {
    private final ServiceFeatureRepository serviceFeatureRepository;
    private final ServiceRepository serviceRepository;
    private final ServiceFeatureMapper serviceFeatureMapper;

    @Override
    public List<ServiceFeatureResponse> getByServiceId(Long serviceId) {
        return serviceFeatureMapper.toResponseList(
                serviceFeatureRepository.findByService_ServiceId(serviceId)
        );
    }

    @Override
    public ServiceFeatureResponse create(ServiceFeatureRequest req) {
        org.project.admin.entity.Service service = serviceRepository.findById(req.getServiceId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dịch vụ"));
        ServiceFeature entity = serviceFeatureMapper.toEntity(req, service);
        entity = serviceFeatureRepository.save(entity);
        return serviceFeatureMapper.toResponse(entity);
    }
}

