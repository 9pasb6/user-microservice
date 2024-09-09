package emazon.microservice.user_microservice.infrastructure.output.jpa.adapter;

import emazon.microservice.user_microservice.domain.model.User;
import emazon.microservice.user_microservice.domain.spi.IUserPersistencePort;
import emazon.microservice.user_microservice.infrastructure.output.jpa.entity.UserEntity;
import emazon.microservice.user_microservice.infrastructure.output.jpa.entity.RoleEntity;
import emazon.microservice.user_microservice.infrastructure.output.jpa.mapper.UserEntityMapper;
import emazon.microservice.user_microservice.infrastructure.output.jpa.repository.UserRepository;
import emazon.microservice.user_microservice.infrastructure.output.jpa.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public void createUser(User user) {
        UserEntity userEntity = userEntityMapper.toEntity(user);
        userRepository.save(userEntity);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .map(userEntityMapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<User> getUsersByRole(String role) {
        return userRepository.findUsersByRoleName(role).stream()
                .map(userEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void assignRoleToUser(Long userId, String roleName) {
        UserEntity user = userRepository.findByIdWithRoles(userId)
                .orElse(null);

        RoleEntity role = roleRepository.findByName(roleName)
                .orElse(null);

        if (user != null && role != null) {
            user.getRoles().add(role);
            userRepository.save(user);
        }
    }

    @Override
    public void removeRoleFromUser(Long userId, String roleName) {
        UserEntity user = userRepository.findByIdWithRoles(userId)
                .orElse(null);

        RoleEntity role = roleRepository.findByName(roleName)
                .orElse(null);

        if (user != null && role != null && user.getRoles().contains(role)) {
            user.getRoles().remove(role);
            userRepository.save(user);
        }
    }

    @Override
    public User findByIdentityDocument(String identityDocument) {
        return userRepository.findByIdentityDocument(identityDocument)
                .map(userEntityMapper::toDomain)
                .orElse(null);
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userEntityMapper::toDomain)
                .orElse(null);
    }




}