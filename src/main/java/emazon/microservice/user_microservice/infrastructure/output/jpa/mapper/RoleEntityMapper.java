package emazon.microservice.user_microservice.infrastructure.output.jpa.mapper;

import emazon.microservice.user_microservice.domain.model.Role;
import emazon.microservice.user_microservice.infrastructure.output.jpa.entity.RoleEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleEntityMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "users", target = "users", ignore = true)
    Role toDomain(RoleEntity roleEntity);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "users", target = "users", ignore = true)
    RoleEntity toEntity(Role role);

}