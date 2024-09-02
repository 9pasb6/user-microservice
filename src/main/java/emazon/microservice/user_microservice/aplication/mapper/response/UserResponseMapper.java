package emazon.microservice.user_microservice.aplication.mapper.response;


import emazon.microservice.user_microservice.aplication.dto.response.RoleResponse;
import emazon.microservice.user_microservice.aplication.dto.response.UserResponse;
import emazon.microservice.user_microservice.domain.model.Role;
import emazon.microservice.user_microservice.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface UserResponseMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "identityDocument", target = "identityDocument")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "rolesToRoleResponses")
    UserResponse userToUserResponse(User user);

    @Named("rolesToRoleResponses")
    default List<RoleResponse> mapRolesToRoleResponses(List<Role> roles) {
        return roles.stream()
                .map(role -> new RoleResponse(role.getName()))
                .collect(Collectors.toList());
    }
}