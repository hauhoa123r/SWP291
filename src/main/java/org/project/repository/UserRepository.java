package org.project.repository;

import org.project.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

    UserEntity findByEmailAndPasswordHash(String email, String passwordHash);

    Optional<UserEntity> findByEmail(String email);

}
