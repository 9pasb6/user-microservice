package emazon.microservice.user_microservice.domain.exception;

public class RoleExceptions {

    private RoleExceptions() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static class RoleNotFoundException extends RuntimeException {
        public RoleNotFoundException(String message) {
            super(message);
        }
    }

    public static class RoleNameAlreadyExistsException extends RuntimeException {
        public RoleNameAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class RoleNameCannotBeNullOrEmptyException extends RuntimeException {
        public RoleNameCannotBeNullOrEmptyException(String message) {
            super(message);
        }
    }

    public static class RoleAssignmentException extends RuntimeException {
        public RoleAssignmentException(String message) {
            super(message);
        }
    }

    public static class RoleNotAssignedToUserException extends RuntimeException {
        public RoleNotAssignedToUserException(String message) {
            super(message);
        }
    }
}