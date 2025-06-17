package org.project.service.impl;

import jakarta.transaction.Transactional;
import org.project.converter.DepartmentConverter;
import org.project.entity.DepartmentEntity;
import org.project.model.response.DepartmentResponse;
import org.project.repository.DepartmentRepository;
import org.project.repository.impl.custom.DepartmentRepsitoryCustom;
import org.project.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;
    private DepartmentRepsitoryCustom departmentRepsitoryCustom;
    private DepartmentConverter departmentConverter;

    @Autowired
    public void setDepartmentRepository(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Autowired
    public void setDepartmentConverter(DepartmentConverter departmentConverter) {
        this.departmentConverter = departmentConverter;
    }

    @Autowired
    public void setDepartmentRepsitoryCustom(DepartmentRepsitoryCustom departmentRepsitoryCustom) {
        this.departmentRepsitoryCustom = departmentRepsitoryCustom;
    }

    @Override
    public List<DepartmentResponse> getAll() {
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
        return departmentEntities.stream().map(departmentConverter::toResponse).toList();
    }

    @Override
    public List<DepartmentResponse> getAllDepartmentsHaveDoctor() {
        List<DepartmentEntity> departmentEntities = departmentRepsitoryCustom.findAllDepartmentsByDoctorRole();
        return departmentEntities.stream().map(departmentConverter::toResponse).toList();
    }

    @Override
    public boolean isDepartmentNameExist(String departmentName) {
        return departmentRepository.existsByName(departmentName);
    }
}
