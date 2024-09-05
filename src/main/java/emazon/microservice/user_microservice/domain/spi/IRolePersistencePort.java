package emazon.microservice.user_microservice.domain.spi;

import emazon.microservice.user_microservice.domain.model.Role;

import java.util.List;
import java.util.Set;

public interface IRolePersistencePort {

    void save(Role rol);

    Role findById(Long id);

    List<Role> findAll();

    Role findByName(String name);

    List<Role> getRolesByIds(Set<Long> ids);
}
