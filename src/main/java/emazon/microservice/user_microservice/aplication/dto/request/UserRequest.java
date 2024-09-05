package emazon.microservice.user_microservice.aplication.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String name;
    private String lastName;
    private String identityDocument;
    private String phoneNumber;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "The birth date format should be yyyy-MM-dd.")
    private String birthDate;
    private String email;
    @NotBlank(message = "The password cannot be empty.")
    private String password;
    private List<Long> rolesId;
}