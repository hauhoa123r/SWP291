package org.project.admin.repository;

import org.project.admin.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("adminUserRepository")
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
//    Optional<User> findByUserRole(UserRole userRole);

    @Query("SELECT u FROM User u WHERE u.deleted = false")
    Page<User> findAllByDeletedFalse(Pageable pageable);

}
