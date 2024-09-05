package emazon.microservice.user_microservice.handler;

import emazon.microservice.user_microservice.aplication.dto.request.UserRequest;
import emazon.microservice.user_microservice.aplication.handler.impl.UserHandler;
import emazon.microservice.user_microservice.domain.api.IUserServicePort;
import emazon.microservice.user_microservice.domain.model.User;
import emazon.microservice.user_microservice.aplication.mapper.request.UserRequestMapper;
import emazon.microservice.user_microservice.aplication.mapper.response.UserResponseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserHandlerTest {

    @InjectMocks
    private UserHandler userHandler;

    @Mock
    private IUserServicePort userServicePort;

    @Mock
    private UserRequestMapper userRequestMapper;

    @Mock
    private UserResponseMapper userResponseMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userRequest = new UserRequest(
                "John", "Doe", "12345678", "555-1234", "1990-01-01",
                "john.doe@example.com", "password123", Collections.emptyList()
        );
    }

    @Test
    void testCreateUserSuccessfully() {
        when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("encodedPassword");
        when(userRequestMapper.requestToUser(userRequest)).thenReturn(new User());


        userHandler.createUser(userRequest);

        verify(userServicePort, times(1)).createUser(any(User.class));
        assertEquals("encodedPassword", userRequest.getPassword());
    }

    @Test
    void testCreateUserWithInvalidBirthDate() {

        userRequest.setBirthDate("01-01-1990");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userHandler.createUser(userRequest);
        });

        assertEquals("The birth date format should be yyyy-MM-dd.", exception.getMessage());
        verify(userServicePort, never()).createUser(any(User.class));
    }


}