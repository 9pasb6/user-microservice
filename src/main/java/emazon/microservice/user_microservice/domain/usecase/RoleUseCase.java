package emazon.microservice.user_microservice.domain.usecase;

import emazon.microservice.user_microservice.domain.api.IRoleServicePort;
import emazon.microservice.user_microservice.domain.model.Role;
import emazon.microservice.user_microservice.domain.spi.IRolePersistencePort;

import java.util.List;

public class RoleUseCase implements IRoleServicePort {

    private final IRolePersistencePort rolePersistencePort;

    public RoleUseCase(IRolePersistencePort rolePersistencePort) {
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public void save(Role rol) {

        rolePersistencePort.save(rol);
    }

    @Override
    public Role findById(Long id) {
        return rolePersistencePort.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return rolePersistencePort.findAll();
    }
}
