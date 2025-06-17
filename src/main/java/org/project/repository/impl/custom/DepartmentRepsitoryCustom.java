package org.project.repository.impl.custom;

import org.project.entity.DepartmentEntity;

import java.util.List;

public interface DepartmentRepsitoryCustom {
    List<DepartmentEntity> findAllDepartmentsByDoctorRole();
}
