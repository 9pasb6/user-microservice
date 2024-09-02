package emazon.microservice.user_microservice.infrastructure.jpa.repository;

import emazon.microservice.user_microservice.infrastructure.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(String name);
}