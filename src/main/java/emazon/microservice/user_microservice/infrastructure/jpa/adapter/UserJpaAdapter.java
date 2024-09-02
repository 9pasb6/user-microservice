package emazon.microservice.user_microservice.infrastructure.jpa.adapter;

import emazon.microservice.user_microservice.domain.model.User;
import emazon.microservice.user_microservice.domain.model.Role;
import emazon.microservice.user_microservice.domain.spi.IUserPersistencePort;
import emazon.microservice.user_microservice.infrastructure.jpa.entity.UserEntity;
import emazon.microservice.user_microservice.infrastructure.jpa.entity.RoleEntity;
import emazon.microservice.user_microservice.infrastructure.jpa.mapper.UserEntityMapper;
import emazon.microservice.user_microservice.infrastructure.jpa.mapper.RoleEntityMapper;
import emazon.microservice.user_microservice.infrastructure.jpa.repository.UserRepository;
import emazon.microservice.user_microservice.infrastructure.jpa.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
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

        if (userEntity.getRoles() == null || userEntity.getRoles().isEmpty()) {
            RoleEntity defaultRole = roleRepository.findByName("CUSTOMER")
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
            userEntity.setRoles(Set.of(defaultRole));
        }
        System.out.println("user = " + user);
        System.out.println("user entitiy = " + userEntity);
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
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userEntityMapper.toDomain(userEntity);
    }


    @Override
    public List<User> getUsersByRole(String role) {
        return userRepository.findUsersByRoleName(role).stream()
                .map(userEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    public void assignRoleToUser(Long userId, String roleName) {
        UserEntity user = userRepository.findByIdWithRoles(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        RoleEntity role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User already has this role");
        }
    }

    @Override
    @Transactional
    public void removeRoleFromUser(Long userId, String roleName) {
        UserEntity user = userRepository.findByIdWithRoles(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        RoleEntity role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        if (user.getRoles().contains(role)) {
            user.getRoles().remove(role);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User does not have this role");
        }

    }
}