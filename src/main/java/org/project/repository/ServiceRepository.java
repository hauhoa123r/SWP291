package org.project.repository;

import org.project.entity.ServiceEntity;
import org.project.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    Optional<ServiceEntity> findByIdAndProductEntityProductStatus(Long id, ProductStatus productEntityProductStatus);

    boolean existsByIdAndProductEntityProductStatusAndDepartmentEntityStaffEntitiesId(Long id, ProductStatus productEntityProductStatus, Long departmentEntityStaffEntitiesId);

    boolean existsByIdAndProductEntityProductStatus(Long id, ProductStatus productEntityProductStatus);
}
