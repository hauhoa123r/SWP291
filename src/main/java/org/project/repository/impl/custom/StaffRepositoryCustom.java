package org.project.repository.impl.custom;

import org.project.entity.StaffEntity;
import org.project.enums.StaffRole;
import org.project.model.dto.MakeAppointmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StaffRepositoryCustom {
    Page<StaffEntity> findAllByStaffRole(StaffRole staffRole, Pageable pageable);

    Page<StaffEntity> findAllByStaffRoleAndDepartmentEntityName(StaffRole staffRole, String departmentEntityName, Pageable pageable);

    List<StaffEntity> findAllByStaffRoleAndDepartmentEntityNameAndIdIsNot(StaffRole staffRole, String departmentEntityName, Long id);

    List<MakeAppointmentDTO> findAllMakeAppointment(MakeAppointmentDTO makeAppointmentDTO);
}
