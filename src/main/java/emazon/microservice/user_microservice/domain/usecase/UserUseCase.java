package emazon.microservice.user_microservice.domain.usecase;

import emazon.microservice.user_microservice.domain.api.IUserServicePort;
import emazon.microservice.user_microservice.domain.model.User;
import emazon.microservice.user_microservice.domain.spi.IUserPersistencePort;
import emazon.microservice.user_microservice.domain.util.ValidationUtil;

import java.util.List;


public class UserUseCase implements IUserServicePort {


    private final IUserPersistencePort userPersistencePort;


    public UserUseCase(IUserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }


    @Override
    public void createUser(User user) {
        ValidationUtil.validateUser(user);
        this.userPersistencePort.createUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userPersistencePort.getAllUsers();
    }

    @Override
    public User getUserById(Long id) {
        return this.userPersistencePort.getUserById(id);
    }


    @Override
    public List<User> getUsersByRole(String role) {
        return this.userPersistencePort.getUsersByRole(role);
    }

    @Override
    public void assignRoleToUser(Long id, String role) {
        this.userPersistencePort.assignRoleToUser(id, role);
    }

    @Override
    public void removeRoleFromUser(Long id, String role) {
        this.userPersistencePort.removeRoleFromUser(id, role);
    }
}