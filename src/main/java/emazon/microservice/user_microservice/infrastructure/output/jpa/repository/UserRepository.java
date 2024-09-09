package emazon.microservice.user_microservice.infrastructure.output.jpa.repository;

import emazon.microservice.user_microservice.infrastructure.output.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r.name = :role")
    List<UserEntity> findUsersByRoleName(@Param("role") String role);

    @Query("SELECT u FROM UserEntity u JOIN FETCH u.roles WHERE u.id = :id")
    Optional<UserEntity> findByIdWithRoles(Long id);

    Optional<UserEntity> findByIdentityDocument(String identityDocument);

    Optional<UserEntity> findByEmail(String email);


}