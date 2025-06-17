package org.project.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.project.entity.DepartmentEntity;
import org.project.repository.impl.custom.DepartmentRepsitoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class DepartmentRepositoryCustomImpl implements DepartmentRepsitoryCustom {
    private EntityManager entityManager;

    @Autowired
     public void setEntityManager(EntityManager entityManager) {
         this.entityManager = entityManager;
     }

    @Override
    public List<DepartmentEntity> findAllDepartmentsByDoctorRole() {
        String jpql = """
                    select de.name from DepartmentEntity de
                    join de.staffEntities se
                    where se.staffRole = 'DOCTOR'
                    group by de.name
                """;
        return entityManager.createQuery(jpql, String.class).getResultList().stream().map(departmentName -> {
            DepartmentEntity departmentEntity = new DepartmentEntity();
            departmentEntity.setName(departmentName);
            return departmentEntity;
        }).toList();
    }
}
