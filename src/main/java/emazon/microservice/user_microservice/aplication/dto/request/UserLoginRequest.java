package emazon.microservice.user_microservice.aplication.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequest {

    @NotBlank(message = "The email cannot be empty.")
    private String email;
    @NotBlank(message = "The password cannot be empty.")
    private String password;
}