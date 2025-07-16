package org.project.admin.repository;

import org.project.admin.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("adminServiceRepository")
public interface ServiceRepository extends JpaRepository<Service, Long> {}