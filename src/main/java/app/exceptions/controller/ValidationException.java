package app.exceptions.controller;

import lombok.Getter;
import org.springframework.validation.Errors;

public class ValidationException extends ControllerException {
    @Getter
    private final Errors errors;

    public ValidationException(Errors errors) {
        this.errors = errors;
    }

}
