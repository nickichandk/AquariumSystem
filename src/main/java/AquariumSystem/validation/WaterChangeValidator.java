package AquariumSystem.validation;


import AquariumSystem.exception.ValidationException;

public class WaterChangeValidator implements NoteValidation {

    public void validate(String note, int number) throws ValidationException {
        //NoteValidator.validate(note, 60);
        if (number > 60) {
            throw new ValidationException("");
        }
    }
}