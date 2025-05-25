package org.project.repository;

import org.project.entity.UserEntity;
import org.project.repository.impl.custom.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

    UserEntity findByUsernameAndPassword(String username, String password);

}
