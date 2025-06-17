package org.project.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.project.entity.StaffEntity;
import org.project.enums.StaffRole;
import org.project.model.dto.MakeAppointmentDTO;
import org.project.repository.impl.custom.StaffRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class StaffRepositoryCustomImpl implements StaffRepositoryCustom {

    private EntityManager entityManager;

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private Page<StaffEntity> getEntitiesByPage(TypedQuery<StaffEntity> getEntitiesTypedQuery, TypedQuery<Long> countTypedQuery, Pageable pageable) {
        getEntitiesTypedQuery.setFirstResult((int) pageable.getOffset());
        getEntitiesTypedQuery.setMaxResults(pageable.getPageSize());
        List<StaffEntity> staffEntities = getEntitiesTypedQuery.getResultList();

        Long total = countTypedQuery.getSingleResult();
        return new PageImpl<>(staffEntities, pageable, total);
    }

    @Override
    public Page<StaffEntity> findAllByStaffRole(StaffRole staffRole, Pageable pageable) {
        String getEntityJpql = """
                select s from StaffEntity s
                left join s.reviewEntities sr
                where s.staffRole = :staffRole
                group by s
                order by avg(sr.rating) desc, count(sr.id) desc
                """;
        TypedQuery<StaffEntity> getEntitiesTypeQuery = entityManager.createQuery(getEntityJpql, StaffEntity.class);
        getEntitiesTypeQuery.setParameter("staffRole", staffRole);

        String countEntityJpql = """
                select count(s) from StaffEntity s
                where s.staffRole = :staffRole
                """;
        TypedQuery<Long> countTypedQuery = entityManager.createQuery(countEntityJpql, Long.class);
        countTypedQuery.setParameter("staffRole", staffRole);

        return getEntitiesByPage(getEntitiesTypeQuery, countTypedQuery, pageable);
    }

    @Override
    public Page<StaffEntity> findAllByStaffRoleAndDepartmentEntityName(StaffRole staffRole, String departmentEntityName, Pageable pageable) {
        String getEntityJpql = """
                select s from StaffEntity s
                left join s.reviewEntities sr
                where s.staffRole = :staffRole and s.departmentEntity.name = :departmentEntityName
                group by s
                order by avg(sr.rating) desc, count(sr.id) desc
                """;
        TypedQuery<StaffEntity> getEntitiesTypedQuery = entityManager.createQuery(getEntityJpql, StaffEntity.class);
        getEntitiesTypedQuery.setParameter("staffRole", staffRole);
        getEntitiesTypedQuery.setParameter("departmentEntityName", departmentEntityName);

        String countEntityJpql = """
                select count(s) from StaffEntity s
                where s.staffRole = :staffRole and s.departmentEntity.name = :departmentEntityName
                """;
        TypedQuery<Long> countTypedQuery = entityManager.createQuery(countEntityJpql, Long.class);
        countTypedQuery.setParameter("staffRole", staffRole);
        countTypedQuery.setParameter("departmentEntityName", departmentEntityName);

        return getEntitiesByPage(getEntitiesTypedQuery, countTypedQuery, pageable);
    }

    @Override
    public List<StaffEntity> findAllByStaffRoleAndDepartmentEntityNameAndIdIsNot(StaffRole staffRole, String departmentEntityName, Long id) {
        String getEntityJpql = """
                select s from StaffEntity s
                where s.staffRole = :staffRole and s.departmentEntity.name = :departmentEntityName and s.id != :id
                """;
        TypedQuery<StaffEntity> getEntitiesTypedQuery = entityManager.createQuery(getEntityJpql, StaffEntity.class);
        getEntitiesTypedQuery.setParameter("staffRole", staffRole);
        getEntitiesTypedQuery.setParameter("departmentEntityName", departmentEntityName);
        getEntitiesTypedQuery.setParameter("id", id);
        return getEntitiesTypedQuery.getResultList();
    }

    @Override
    public List<MakeAppointmentDTO> findAllMakeAppointment(MakeAppointmentDTO makeAppointmentDTO) {
        return List.of();
    }
}
