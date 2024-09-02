package emazon.microservice.user_microservice.aplication.mapper.response;

import emazon.microservice.user_microservice.aplication.dto.response.RoleResponse;
import emazon.microservice.user_microservice.domain.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface RoleResponseMapper {

    @Mapping(source = "name", target = "name")
    RoleResponse roleToResponse(Role role);
}