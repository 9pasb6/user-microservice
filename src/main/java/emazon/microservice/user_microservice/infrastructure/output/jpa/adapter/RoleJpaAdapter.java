package emazon.microservice.user_microservice.infrastructure.output.jpa.adapter;

import emazon.microservice.user_microservice.domain.model.Role;
import emazon.microservice.user_microservice.domain.spi.IRolePersistencePort;
import emazon.microservice.user_microservice.infrastructure.output.jpa.entity.RoleEntity;
import emazon.microservice.user_microservice.infrastructure.output.jpa.mapper.RoleEntityMapper;
import emazon.microservice.user_microservice.infrastructure.output.jpa.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {

    private final RoleRepository roleRepository;
    private final RoleEntityMapper roleEntityMapper;

    @Override
    public void save(Role role) {
        RoleEntity roleEntity = roleEntityMapper.toEntity(role);
        roleRepository.save(roleEntity);
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id)
                .map(roleEntityMapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll().stream()
                .map(roleEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .map(roleEntityMapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<Role> getRolesByIds(Set<Long> ids) {
        return roleRepository.findAllById(ids)
                .stream()
                .map(roleEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
}