package emazon.microservice.user_microservice.aplication.mapper.request;

import emazon.microservice.user_microservice.aplication.dto.request.UserLoginRequest;
import emazon.microservice.user_microservice.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface UserLoginRequestMapper {

    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    User requestToUser(UserLoginRequest request);

}