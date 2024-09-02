package emazon.microservice.user_microservice.domain.api;

import emazon.microservice.user_microservice.domain.model.User;

import java.util.List;

public interface IUserServicePort {


    void createUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    List<User> getUsersByRole(String role);

    void assignRoleToUser(Long id, String role);

    void removeRoleFromUser(Long id, String role);


}