package emazon.microservice.user_microservice.aplication.handler.impl;

import emazon.microservice.user_microservice.aplication.dto.request.UserRequest;
import emazon.microservice.user_microservice.aplication.dto.response.UserResponse;
import emazon.microservice.user_microservice.aplication.handler.IUserHandler;
import emazon.microservice.user_microservice.aplication.mapper.request.UserRequestMapper;
import emazon.microservice.user_microservice.aplication.mapper.response.UserResponseMapper;
import emazon.microservice.user_microservice.domain.api.IUserServicePort;
import emazon.microservice.user_microservice.domain.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final UserRequestMapper userRequestMapper;
    private final UserResponseMapper userResponseMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void createUser(@Valid UserRequest userRequest) {

        LocalDate birthDate = convertAndValidateBirthDate(userRequest.getBirthDate());

        userRequest.setBirthDate(birthDate.toString());

        String encryptedPassword = passwordEncoder.encode(userRequest.getPassword());
        userRequest.setPassword(encryptedPassword);

        User user = userRequestMapper.requestToUser(userRequest);
        userServicePort.createUser(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userServicePort.getAllUsers();
        return users.stream()
                .map(userResponseMapper::userToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userServicePort.getUserById(id);
        return userResponseMapper.userToUserResponse(user);
    }

    @Override
    public List<UserResponse> getUsersByRole(String role) {
        List<User> users = userServicePort.getUsersByRole(role);
        return users.stream()
                .map(userResponseMapper::userToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void assignRoleToUser(Long id, String role) {
        userServicePort.assignRoleToUser(id, role);
    }

    @Override
    public void removeRoleFromUser(Long id, String role) {
        userServicePort.removeRoleFromUser(id, role);
    }

    private LocalDate convertAndValidateBirthDate(String birthDateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(birthDateString, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("The birth date format should be yyyy-MM-dd.");
        }
    }
}