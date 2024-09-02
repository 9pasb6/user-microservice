package emazon.microservice.user_microservice.infrastructure.jpa.mapper;

import emazon.microservice.user_microservice.domain.model.Role;
import emazon.microservice.user_microservice.infrastructure.jpa.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleEntityMapper {
    RoleEntityMapper INSTANCE = Mappers.getMapper(RoleEntityMapper.class);

    Role toDomain(RoleEntity roleEntity);
    RoleEntity toEntity(Role role);
}