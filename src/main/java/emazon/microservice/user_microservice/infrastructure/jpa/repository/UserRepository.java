package emazon.microservice.user_microservice.infrastructure.jpa.repository;

import emazon.microservice.user_microservice.infrastructure.jpa.entity.RoleEntity;
import emazon.microservice.user_microservice.infrastructure.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r.name = :role")
    List<UserEntity> findUsersByRoleName(@Param("role") String role);

    @Query("SELECT u FROM UserEntity u JOIN FETCH u.roles WHERE u.id = :id")
    Optional<UserEntity> findByIdWithRoles(Long id);


}