package emazon.microservice.user_microservice.aplication.handler;

import emazon.microservice.user_microservice.aplication.dto.request.UserLoginRequest;
import emazon.microservice.user_microservice.aplication.dto.request.UserRequest;
import emazon.microservice.user_microservice.aplication.dto.response.UserLoginResponse;
import emazon.microservice.user_microservice.aplication.dto.response.UserResponse;
import emazon.microservice.user_microservice.domain.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserHandler {

    void createUser( UserRequest userRequest );

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    List<UserResponse> getUsersByRole(String role);

    void assignRoleToUser(Long id, String role);

    void removeRoleFromUser(Long id, String role);

    UserLoginResponse login(UserLoginRequest userLoginRequest);
}
