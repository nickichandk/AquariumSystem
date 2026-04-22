package AquariumSystem.validation;

import AquariumSystem.exception.ValidationException;

public interface NoteValidation {
    void validate(String note, int number) throws ValidationException;
}
