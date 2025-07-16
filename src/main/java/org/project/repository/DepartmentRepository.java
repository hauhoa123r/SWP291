package org.project.repository;

import org.project.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
    boolean existsByName(String name);
    DepartmentEntity findByNameContaining(String name);
}
