package org.project.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.project.entity.UserEntity;
import org.project.repository.impl.custom.UserRepositoryCustom;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public UserEntity findByUsername(String username) {
        StringBuilder sql = new StringBuilder("SELECT u FROM UserEntity u WHERE u.username = :username");
        return (UserEntity)entityManager.createQuery(sql.toString()).setParameter("username", username).getSingleResult();
    }

    @Override
    public void deleteByUsername(String username) {

    }

    @Override
    public void updateUser(UserEntity userEntity) {

    }

    @Override
    public void save(UserEntity userEntity) {

    }
}
