package emazon.microservice.user_microservice.aplication.mapper.request;

import emazon.microservice.user_microservice.aplication.dto.request.RoleRequest;
import emazon.microservice.user_microservice.domain.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface RoleRequestMapper {

    @Mapping(source = "name", target = "name")
    Role requestToRole(RoleRequest roleRequest);
}