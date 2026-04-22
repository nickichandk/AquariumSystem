package AquariumSystem.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super("Fejl: Det er ikke muligt at validere den ønskede handling");
    }
}
