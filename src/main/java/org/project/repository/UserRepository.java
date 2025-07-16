package org.project.repository;

import org.project.entity.UserEntity;
import org.project.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByIdAndUserStatus(Long id, UserStatus userStatus);

    boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.passwordHash = ?2 WHERE u.email = ?1")
    void updatePassword(String email, String password);
}
