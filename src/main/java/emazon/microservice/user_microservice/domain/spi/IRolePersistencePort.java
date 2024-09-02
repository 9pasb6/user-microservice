package emazon.microservice.user_microservice.domain.spi;

import emazon.microservice.user_microservice.domain.model.Role;

import java.util.List;

public interface IRolePersistencePort {

    void save(Role rol);

    Role findById(Long id);

    List<Role> findAll();
}
