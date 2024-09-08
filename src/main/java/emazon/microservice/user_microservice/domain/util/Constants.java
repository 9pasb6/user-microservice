package emazon.microservice.user_microservice.domain.util;

public class Constants {

    public static final String USER_NAME_EMPTY = "The user name cannot be empty or null.";
    public static final String USER_LAST_NAME_EMPTY = "The user last name cannot be empty or null.";
    public static final String IDENTITY_DOCUMENT_INVALID = "The identity document must be numeric and cannot be empty or null.";
    public static final String PHONE_NUMBER_INVALID = "The phone number must be valid and cannot be empty or null.";
    public static final String USER_NOT_ADULT = "The user must be an adult.";
    public static final String EMAIL_INVALID = "The email must be valid and cannot be empty or null.";
    public static final String PASSWORD_EMPTY = "The password cannot be empty or null.";
    public static final String BIRTH_DATE_INVALID_FORMAT = "The birth date format is invalid.";

    public static final String DEFAULT_ROLE_NOT_FOUND = "Default role not found.";
    public static final String USER_NOT_FOUND = "User not found with id: ";
    public static final String ROLE_NOT_FOUND = "Role not found with name: ";
    public static final String MAIL_NOT_FOUND = "Email not found with email: ";
    public static final String USER_ALREADY_HAS_ROLE = "The user already has the role: ";
    public static final String ROLE_NOT_ASSIGNED = "The user does not have the role: ";
    public static final String IDENTITY_DOCUMENT_ALREADY_EXISTS = "A user with this identity document already exists.";
    public static final String ROLES_NOT_FOUND_FOR_IDS = "Role not found for ID: ";
    public static final String SOME_ROLES_NOT_FOUND_FOR_IDS = "Some Roles not found for IDs: ";
    public static final String PASSWORD_INCORRECT = "Password is incorrect. : ";
    public static final String DEFAULT_ROLE = "ROLE_CUSTOMER";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String ACCOUNT_LOCKED = "Account is locked due to multiple failed login attempts.";
    public static final int MAX_FAILED_ATTEMPTS = 5;
    public static final String ROLES = "roles";
}