package emazon.microservice.user_microservice.aplication.mapper.request;

import emazon.microservice.user_microservice.aplication.dto.request.UserRequest;
import emazon.microservice.user_microservice.domain.model.Role;
import emazon.microservice.user_microservice.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface UserRequestMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "identityDocument", target = "identityDocument")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "birthDate", target = "birthDate")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "rolesId", target = "roles", qualifiedByName = "rolesIdToRoles")
    User requestToUser(UserRequest request);

    @Named("rolesIdToRoles")
    default List<Role> mapRolesIdToRoles(List<Long> rolesId) {
        return rolesId.stream()
                .map(id -> new Role(id, null,null)).toList();
    }
}