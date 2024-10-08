package emazon.microservice.user_microservice.aplication.handler.impl;

import emazon.microservice.user_microservice.aplication.dto.request.UserLoginRequest;
import emazon.microservice.user_microservice.aplication.dto.request.UserRequest;
import emazon.microservice.user_microservice.aplication.dto.response.UserLoginResponse;
import emazon.microservice.user_microservice.aplication.dto.response.UserResponse;
import emazon.microservice.user_microservice.aplication.handler.IUserHandler;
import emazon.microservice.user_microservice.aplication.mapper.request.UserRequestMapper;
import emazon.microservice.user_microservice.aplication.mapper.response.UserResponseMapper;
import emazon.microservice.user_microservice.aplication.util.Constants;
import emazon.microservice.user_microservice.aplication.util.security.JwtService;
import emazon.microservice.user_microservice.domain.api.IUserServicePort;
import emazon.microservice.user_microservice.domain.model.Role;
import emazon.microservice.user_microservice.domain.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
    private final JwtService jwtService;

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


    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {

        User user = userServicePort.login(userLoginRequest.getEmail());

        boolean passwordMatch = passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword());

        if (!passwordMatch) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Constants.PASSWORD_INCORRECT + userLoginRequest.getPassword());
        }

        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        String token = jwtService.generateToken(user.getEmail(), roles);

        return new UserLoginResponse(token, user.getName(), roles);
    }


    private LocalDate convertAndValidateBirthDate(String birthDateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
        try {
            return LocalDate.parse(birthDateString, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(Constants.BIRTH_DATE_INVALID_FORMAT);
        }
    }
}