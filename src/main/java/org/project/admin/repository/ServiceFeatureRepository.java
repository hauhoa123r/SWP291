package org.project.admin.repository;

import org.project.admin.entity.ServiceFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("adminServiceFeatureRepository")
public interface ServiceFeatureRepository extends JpaRepository<ServiceFeature, Long> {
    List<ServiceFeature> findByService_ServiceId(Long serviceId);
}

