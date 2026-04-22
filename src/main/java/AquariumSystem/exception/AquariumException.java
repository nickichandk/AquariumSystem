package AquariumSystem.exception;

public class AquariumException extends RuntimeException {
    public AquariumException(String message) {
        super("Fejl: der er ikke mere vand");
    }
}