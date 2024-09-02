package emazon.microservice.user_microservice.infrastructure.configuration;

import emazon.microservice.user_microservice.domain.api.IRoleServicePort;
import emazon.microservice.user_microservice.domain.api.IUserServicePort;
import emazon.microservice.user_microservice.domain.spi.IRolePersistencePort;
import emazon.microservice.user_microservice.domain.spi.IUserPersistencePort;
import emazon.microservice.user_microservice.domain.usecase.RoleUseCase;
import emazon.microservice.user_microservice.domain.usecase.UserUseCase;
import emazon.microservice.user_microservice.infrastructure.jpa.adapter.RoleJpaAdapter;
import emazon.microservice.user_microservice.infrastructure.jpa.adapter.UserJpaAdapter;
import emazon.microservice.user_microservice.infrastructure.jpa.mapper.RoleEntityMapper;
import emazon.microservice.user_microservice.infrastructure.jpa.mapper.UserEntityMapper;
import emazon.microservice.user_microservice.infrastructure.jpa.repository.RoleRepository;
import emazon.microservice.user_microservice.infrastructure.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    @Bean
    public IUserPersistencePort userPersistencePort(UserEntityMapper userEntityMapper, UserRepository userRepository, RoleRepository roleRepository) {
        return new UserJpaAdapter(userRepository, roleRepository, userEntityMapper);
    }

    @Bean
    public IRolePersistencePort rolePersistencePort(RoleEntityMapper roleEntityMapper, RoleRepository roleRepository) {
        return new RoleJpaAdapter(roleRepository, roleEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort(IUserPersistencePort userPersistencePort) {
        return new UserUseCase(userPersistencePort);
    }

    @Bean
    public IRoleServicePort roleServicePort(IRolePersistencePort rolePersistencePort) {
        return new RoleUseCase(rolePersistencePort);
    }
}