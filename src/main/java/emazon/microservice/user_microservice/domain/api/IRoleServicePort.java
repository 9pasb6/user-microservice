package emazon.microservice.user_microservice.domain.api;

import emazon.microservice.user_microservice.domain.model.Role;

import java.util.List;

public interface IRoleServicePort {

    void save(Role rol);

    Role findById(Long id);

    List<Role> findAll();
}
