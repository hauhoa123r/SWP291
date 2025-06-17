package org.project.repository;

import org.project.entity.PharmacistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PharmacyRepository extends JpaRepository<PharmacistEntity, Long> {

}
