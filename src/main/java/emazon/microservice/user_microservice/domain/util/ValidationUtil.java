package emazon.microservice.user_microservice.domain.util;

import emazon.microservice.user_microservice.domain.model.User;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@[\\w-\\.]+\\.[a-zA-Z]{2,4}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?\\d{1,13}$");
    private static final Pattern ID_DOCUMENT_PATTERN = Pattern.compile("^\\d+$");

    public static void validateUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("The user name cannot be empty or null.");
        }
        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new IllegalArgumentException("The user last name cannot be empty or null.");
        }
        if (user.getIdentityDocument() == null || !ID_DOCUMENT_PATTERN.matcher(user.getIdentityDocument()).matches()) {
            throw new IllegalArgumentException("The identity document must be numeric and cannot be empty or null.");
        }
        if (user.getPhoneNumber() == null || !PHONE_PATTERN.matcher(user.getPhoneNumber()).matches()) {
            throw new IllegalArgumentException("The phone number must be valid and cannot be empty or null.");
        }
        if (user.getBirthDate() == null || !isAdult(user.getBirthDate())) {
            throw new IllegalArgumentException("The user must be an adult.");
        }
        if (user.getEmail() == null || !EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new IllegalArgumentException("The email must be valid and cannot be empty or null.");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("The password cannot be empty or null.");
        }
    }

    private static boolean isAdult(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }
}