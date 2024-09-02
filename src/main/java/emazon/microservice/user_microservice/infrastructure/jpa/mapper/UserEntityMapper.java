package emazon.microservice.user_microservice.infrastructure.jpa.mapper;

import emazon.microservice.user_microservice.domain.model.User;
import emazon.microservice.user_microservice.infrastructure.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserEntityMapper {
    User toDomain(UserEntity userEntity);
    UserEntity toEntity(User user);
}