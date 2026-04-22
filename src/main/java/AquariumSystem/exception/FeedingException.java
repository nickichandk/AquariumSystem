package AquariumSystem.exception;

public class FeedingException extends RuntimeException {
    public FeedingException(String message) {
        super("Fiskene kan ikke fodres");
    }
}
