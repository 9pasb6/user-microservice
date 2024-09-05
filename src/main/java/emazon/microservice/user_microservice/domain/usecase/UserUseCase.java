package emazon.microservice.user_microservice.domain.usecase;

import emazon.microservice.user_microservice.domain.api.IUserServicePort;
import emazon.microservice.user_microservice.domain.exception.UserExceptions;
import emazon.microservice.user_microservice.domain.exception.RoleExceptions;
import emazon.microservice.user_microservice.domain.model.Role;
import emazon.microservice.user_microservice.domain.model.User;
import emazon.microservice.user_microservice.domain.spi.IRolePersistencePort;
import emazon.microservice.user_microservice.domain.spi.IUserPersistencePort;
import emazon.microservice.user_microservice.domain.util.Constants;
import emazon.microservice.user_microservice.domain.util.ValidationUtil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IRolePersistencePort rolePersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IRolePersistencePort rolePersistencePort) {
        this.userPersistencePort = userPersistencePort;
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public void createUser(User user) {

        if (userPersistencePort.findByIdentityDocument(user.getIdentityDocument()) != null) {
            throw new UserExceptions.UserAlreadyExistsException(Constants.IDENTITY_DOCUMENT_ALREADY_EXISTS);
        }


        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Role defaultRole = rolePersistencePort.findByName(Constants.DEFAULT_ROLE);
            if (defaultRole == null) {
                throw new RoleExceptions.RoleNotFoundException(Constants.DEFAULT_ROLE_NOT_FOUND);
            }
            user.setRoles(List.of(defaultRole));
        } else {
            Set<Long> roleIds = user.getRoles().stream()
                    .map(Role::getId)
                    .collect(Collectors.toSet());

            List<Role> existingRoles = rolePersistencePort.getRolesByIds(roleIds);

            if (existingRoles.isEmpty()) {
                throw new RoleExceptions.RoleNotFoundException(Constants.ROLES_NOT_FOUND_FOR_IDS + roleIds);
            }
            if (existingRoles.size() != roleIds.size()) {
                throw new RoleExceptions.RoleNotFoundException(Constants.SOME_ROLES_NOT_FOUND_FOR_IDS + roleIds);
            }
            user.setRoles(existingRoles);
        }

        ValidationUtil.validateUser(user);

        this.userPersistencePort.createUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userPersistencePort.getAllUsers();
    }

    @Override
    public User getUserById(Long id) {
        User user = this.userPersistencePort.getUserById(id);
        if (user == null) {
            throw new UserExceptions.UserNotFoundException(Constants.USER_NOT_FOUND + id);
        }
        return user;
    }

    @Override
    public List<User> getUsersByRole(String role) {
        return this.userPersistencePort.getUsersByRole(role);
    }

    @Override
    public void assignRoleToUser(Long id, String roleName) {
        User user = userPersistencePort.getUserById(id);
        if (user == null) {
            throw new UserExceptions.UserNotFoundException(Constants.USER_NOT_FOUND + id);
        }

        Role role = rolePersistencePort.findByName(roleName);
        if (role == null) {
            throw new RoleExceptions.RoleNotFoundException(Constants.ROLE_NOT_FOUND + roleName);
        }

        if (user.getRoles().contains(role)) {
            throw new UserExceptions.RoleAlreadyAssignedException(Constants.USER_ALREADY_HAS_ROLE + roleName);
        }

        user.getRoles().add(role);
        userPersistencePort.createUser(user);
    }

    @Override
    public void removeRoleFromUser(Long id, String roleName) {
        User user = userPersistencePort.getUserById(id);
        if (user == null) {
            throw new UserExceptions.UserNotFoundException(Constants.USER_NOT_FOUND + id);
        }
        Role role = rolePersistencePort.findByName(roleName);
        if (role == null) {
            throw new RoleExceptions.RoleNotFoundException(Constants.ROLE_NOT_FOUND + roleName);
        }
        if (user.getRoles() == null || !user.getRoles().contains(role)) {
            throw new RoleExceptions.RoleNotAssignedToUserException(Constants.ROLE_NOT_ASSIGNED + roleName);
        }
        user.getRoles().remove(role);
        userPersistencePort.createUser(user);
    }
}