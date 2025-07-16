package org.project.repository;

import org.project.entity.TestRequestEntity;
import org.project.enums.RequestStatus;
import org.project.repository.impl.AssignmentRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<TestRequestEntity, Long>, AssignmentRepositoryCustom {
    Page<TestRequestEntity> findAll(Pageable pageable);
    Page<TestRequestEntity> findAllByRequestStatus(RequestStatus status, Pageable pageable);
}
