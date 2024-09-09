package emazon.microservice.user_microservice.handler;

import emazon.microservice.user_microservice.aplication.dto.request.UserLoginRequest;
import emazon.microservice.user_microservice.aplication.dto.request.UserRequest;
import emazon.microservice.user_microservice.aplication.dto.response.UserLoginResponse;
import emazon.microservice.user_microservice.aplication.dto.response.UserResponse;
import emazon.microservice.user_microservice.aplication.handler.impl.UserHandler;
import emazon.microservice.user_microservice.aplication.mapper.request.UserRequestMapper;
import emazon.microservice.user_microservice.aplication.mapper.response.UserResponseMapper;
import emazon.microservice.user_microservice.aplication.util.Constants;
import emazon.microservice.user_microservice.aplication.util.security.JwtService;
import emazon.microservice.user_microservice.domain.api.IUserServicePort;
import emazon.microservice.user_microservice.domain.model.Role;
import emazon.microservice.user_microservice.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserHandlerTest {

    @Mock
    private IUserServicePort userServicePort;

    @Mock
    private UserRequestMapper userRequestMapper;

    @Mock
    private UserResponseMapper userResponseMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserHandler userHandler;

    private UserRequest userRequest;
    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest();
        userRequest.setBirthDate("1990-01-01");
        userRequest.setPassword("password123");

        user = new User();
        user.setPassword("encryptedPassword");
        role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");

        user.setRoles(Collections.singletonList(role));
    }

    @Test
    void createUser_success() {
        // Arrange
        when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword");
        when(userRequestMapper.requestToUser(any())).thenReturn(user);

        // Act
        userHandler.createUser(userRequest);

        // Assert
        verify(userServicePort, times(1)).createUser(user);
    }

    @Test
    void getAllUsers_success() {
        // Arrange
        when(userServicePort.getAllUsers()).thenReturn(Collections.singletonList(user));
        when(userResponseMapper.userToUserResponse(any())).thenReturn(new UserResponse());

        // Act
        List<UserResponse> users = userHandler.getAllUsers();

        // Assert
        assertEquals(1, users.size());
        verify(userServicePort, times(1)).getAllUsers();
        verify(userResponseMapper, times(1)).userToUserResponse(any());
    }

    @Test
    void login_success() {
        // Arrange
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        // Crear un objeto User con los valores necesarios
        User user = new User();
        user.setEmail("test@example.com"); // Asignar el email
        user.setPassword("encryptedPassword");
        role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");
        user.setRoles(List.of(role)); // Simular los roles del usuario

        // Simular las interacciones con los mocks
        when(userServicePort.login(loginRequest.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtService.generateToken(eq("test@example.com"), anyList())).thenReturn("jwtToken");

        // Act
        UserLoginResponse response = userHandler.login(loginRequest);

        // Assert
        assertEquals("jwtToken", response.getToken());
        verify(userServicePort, times(1)).login(loginRequest.getEmail());
        verify(jwtService, times(1)).generateToken(eq("test@example.com"), anyList());
    }

    @Test
    void login_invalidPassword() {
        // Arrange
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("wrongPassword");

        when(userServicePort.login(loginRequest.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> userHandler.login(loginRequest));
        verify(userServicePort, times(1)).login(loginRequest.getEmail());
        verify(passwordEncoder, times(1)).matches(anyString(), anyString());
    }
}