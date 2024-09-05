package emazon.microservice.user_microservice.infrastructure.output.jpa.mapper;

import emazon.microservice.user_microservice.domain.model.User;
import emazon.microservice.user_microservice.domain.model.Role;
import emazon.microservice.user_microservice.infrastructure.output.jpa.entity.UserEntity;
import emazon.microservice.user_microservice.infrastructure.output.jpa.entity.RoleEntity;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserEntityMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "identityDocument", target = "identityDocument")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "setRoleEntitiesToRoles")
    User toDomain(UserEntity userEntity);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "identityDocument", target = "identityDocument")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "listRolesToSetRoleEntities")
    UserEntity toEntity(User user);

    @Named("setRoleEntitiesToRoles")
    default List<Role> setRoleEntitiesToRoles(Set<RoleEntity> roleEntities) {
        return roleEntities.stream()
                .map(roleEntity -> new Role(roleEntity.getId(), roleEntity.getName(), null))
                .collect(Collectors.toList());
    }

    @Named("listRolesToSetRoleEntities")
    default Set<RoleEntity> listRolesToSetRoleEntities(List<Role> roles) {
        return roles.stream()
                .map(role -> {
                    RoleEntity roleEntity = new RoleEntity();
                    roleEntity.setId(role.getId());
                    roleEntity.setName(role.getName());
                    return roleEntity;
                })
                .collect(Collectors.toSet());
    }
}