package emazon.microservice.user_microservice.domain.util;

import emazon.microservice.user_microservice.domain.model.User;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@[\\w-\\.]+\\.[a-zA-Z]{2,4}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?\\d{1,13}$");
    private static final Pattern ID_DOCUMENT_PATTERN = Pattern.compile("^\\d+$");

    public static void validateUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException(Constants.USER_NAME_EMPTY);
        }
        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new IllegalArgumentException(Constants.USER_LAST_NAME_EMPTY);
        }
        if (user.getIdentityDocument() == null || !ID_DOCUMENT_PATTERN.matcher(user.getIdentityDocument()).matches()) {
            throw new IllegalArgumentException(Constants.IDENTITY_DOCUMENT_INVALID);
        }
        if (user.getPhoneNumber() == null || !PHONE_PATTERN.matcher(user.getPhoneNumber()).matches()) {
            throw new IllegalArgumentException(Constants.PHONE_NUMBER_INVALID);
        }
        if (user.getBirthDate() == null ) {
            throw new IllegalArgumentException(Constants.BIRTH_DATE_INVALID_FORMAT);
        }
        if (!isAdult(user.getBirthDate())) {
            throw new IllegalArgumentException(Constants.USER_NOT_ADULT);
        }
        if (user.getEmail() == null || !EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new IllegalArgumentException(Constants.EMAIL_INVALID);
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException(Constants.PASSWORD_EMPTY);
        }
        if (user.getPassword().length() < 8) {
            throw new IllegalArgumentException(Constants.PASSWORD_EMPTY);
        }
    }

    private static boolean isAdult(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }


}