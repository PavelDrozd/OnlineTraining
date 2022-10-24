package app.controllers;

import app.exceptions.ServiceException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    public String handleServiceException(Model model, ServiceException e) {
        model.addAttribute("error", "Error: " + e.getClass().getSimpleName());
        return "error";
    }
    @ExceptionHandler
    public String handleRuntimeException(Model model, RuntimeException e) {
        model.addAttribute("error", "Error: " + e.getClass().getSimpleName());
        return "error";
    }
}
