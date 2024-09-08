package emazon.microservice.user_microservice.aplication.mapper.response;

import emazon.microservice.user_microservice.aplication.dto.response.UserLoginResponse;
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
public interface UserLoginResponseMapper {

    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.roles", target = "roles", qualifiedByName = "rolesToStringList")
    @Mapping(source = "token", target = "token")
    UserLoginResponse userToResponse(User user, String token);

    @Named("rolesToStringList")
    default List<String> rolesToStringList(List<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}