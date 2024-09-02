package emazon.microservice.user_microservice.aplication.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String name;
    private String lastName;
    private String identityDocument;
    private String phoneNumber;
    private LocalDate birthDate;
    private String email;
    private String password;
    private List<Long> rolesId;
}