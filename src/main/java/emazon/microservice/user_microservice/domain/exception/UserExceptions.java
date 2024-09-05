package emazon.microservice.user_microservice.domain.exception;

public class UserExceptions {

    private UserExceptions() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }


    public static class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class UserEmailAlreadyExistsException extends RuntimeException {
        public UserEmailAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class UserNameCannotBeNullOrEmptyException extends RuntimeException {
        public UserNameCannotBeNullOrEmptyException(String message) {
            super(message);
        }
    }

    public static class UserRoleAssignmentException extends RuntimeException {
        public UserRoleAssignmentException(String message) {
            super(message);
        }
    }

    public static class RoleAlreadyAssignedException extends RuntimeException {
        public RoleAlreadyAssignedException(String message) {
            super(message);
        }
    }
}