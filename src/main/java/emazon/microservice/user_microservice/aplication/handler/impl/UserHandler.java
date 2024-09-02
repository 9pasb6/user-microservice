package emazon.microservice.user_microservice.aplication.handler.impl;

import emazon.microservice.user_microservice.aplication.dto.request.UserRequest;
import emazon.microservice.user_microservice.aplication.dto.response.UserResponse;
import emazon.microservice.user_microservice.aplication.handler.IUserHandler;
import emazon.microservice.user_microservice.aplication.mapper.request.UserRequestMapper;
import emazon.microservice.user_microservice.aplication.mapper.response.UserResponseMapper;
import emazon.microservice.user_microservice.domain.api.IUserServicePort;
import emazon.microservice.user_microservice.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final UserRequestMapper userRequestMapper;
    private final UserResponseMapper userResponseMapper;

    @Override
    public void createUser(UserRequest userRequest) {

        System.out.println("userRequest handler= " + userRequest);
        // Convertir el UserRequest a User utilizando el mapper
        User user = userRequestMapper.requestToUser(userRequest);
        // Llamar al servicio para crear el usuario
        userServicePort.createUser(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        // Obtener todos los usuarios del servicio
        List<User> users = userServicePort.getAllUsers();
        // Convertir la lista de usuarios a UserResponse utilizando el mapper
        return users.stream()
                .map(userResponseMapper::userToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(Long id) {
        // Obtener el usuario por ID del servicio
        User user = userServicePort.getUserById(id);
        // Convertir el usuario a UserResponse utilizando el mapper
        return userResponseMapper.userToUserResponse(user);
    }

    @Override
    public List<UserResponse> getUsersByRole(String role) {
        // Obtener la lista de usuarios por rol del servicio
        List<User> users = userServicePort.getUsersByRole(role);
        // Convertir la lista de usuarios a UserResponse utilizando el mapper
        return users.stream()
                .map(userResponseMapper::userToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void assignRoleToUser(Long id, String role) {
        // Llamar al servicio para asignar un rol al usuario
        userServicePort.assignRoleToUser(id, role);
    }

    @Override
    public void removeRoleFromUser(Long id, String role) {
        // Llamar al servicio para quitar un rol del usuario
        userServicePort.removeRoleFromUser(id, role);
    }
}