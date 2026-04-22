package AquariumSystem;

import AquariumSystem.validation.NoteValidator;
import AquariumSystem.exception.ValidationException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationTest {

    // A normal valid note should work without any errors
    // "assertDoesNotThrow" checks that NO exception is thrown
    @Test
    void validNote_doesNotThrow(){
        assertDoesNotThrow(() -> NoteValidator.validate("30% water changed", 60));
    }

    // An empty note should NOT be allowed
    // "assertThrows" checks that a ValidationException IS thrown
    @Test
    void emptyNote_throwsException(){
        assertThrows(ValidationException.class, () -> NoteValidator.validate("", 60));
    }

    // A null note should NOT be allowed
    // null means the variable has no value at all - also invalid
    @Test
    void nullNote_throwsException(){
        assertThrows(ValidationException.class, () -> NoteValidator.validate(null, 60));
    }

    // A note that is too long should NOT be allowed
    // "x".repeat(61) creates a String of 61 x's - one over the limit of 60
    @Test
    void noteTooLong_throwsException(){
        String longNote = "x".repeat(61);
        assertThrows(ValidationException.class, () -> NoteValidator.validate(longNote, 60));
    }

    // A note is exactly 60 characters should be allowed
    // This is an edge case test - we test right at the edge of the limit
    // "x".repeat(60) creates a String of exactly 60 x's
    @Test
    void noteExcatlyMaxLength_doesNotThrow(){
        String exactNote = "x".repeat(60);
        assertDoesNotThrow(() -> NoteValidator.validate(exactNote, 60));
    }
}
