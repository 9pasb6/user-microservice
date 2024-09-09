package emazon.microservice.user_microservice.infrastructure.input.rest.controllers;

import emazon.microservice.user_microservice.aplication.dto.request.UserLoginRequest;
import emazon.microservice.user_microservice.aplication.dto.request.UserRequest;
import emazon.microservice.user_microservice.aplication.dto.response.UserLoginResponse;
import emazon.microservice.user_microservice.aplication.dto.response.UserResponse;
import emazon.microservice.user_microservice.aplication.handler.IUserHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserHandler userHandler;


    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserRequest userRequest) {

        userHandler.createUser(userRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userHandler.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userHandler.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponse>> getUsersByRole(@PathVariable String role) {
        List<UserResponse> users = userHandler.getUsersByRole(role);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/{id}/roles/{role}")
    public ResponseEntity<Void> assignRoleToUser(@PathVariable Long id, @PathVariable String role) {
        userHandler.assignRoleToUser(id, role);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}/roles/{role}")
    public ResponseEntity<Void> removeRoleFromUser(@PathVariable Long id, @PathVariable String role) {
        userHandler.removeRoleFromUser(id, role);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> loginUser(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        UserLoginResponse userLoginResponse = userHandler.login(userLoginRequest);
        return ResponseEntity.ok(userLoginResponse);
    }
}