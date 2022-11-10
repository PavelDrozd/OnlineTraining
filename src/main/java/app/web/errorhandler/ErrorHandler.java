package app.web.errorhandler;

import app.exceptions.ApplicationException;
import app.exceptions.controller.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice("app.web.view")
public class ErrorHandler {

    private static final String ATTR_ERROR = "error";
    private static final String ERROR_PAGE = "error";
    private static final String ATTR_MESSAGE = "message";
    private static final String SERVER_ERROR = "Server Error";
    private static final String CLIENT_ERROR = "Client Error";
    private static final String NOT_FOUND = "Not Found";
    private static final String SERVER_ERROR_DEFAULT_MESSAGE = "Something went wrong...";

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String error(NotFoundException e, Model model) {
        model.addAttribute(ATTR_ERROR, NOT_FOUND);
        model.addAttribute(ATTR_MESSAGE, e.getMessage());
        return ERROR_PAGE;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String error(ApplicationException e, Model model) {
        model.addAttribute(ATTR_ERROR, CLIENT_ERROR);
        model.addAttribute(ATTR_MESSAGE, e.getMessage());
        return ERROR_PAGE;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String error(Exception e, Model model) {
        model.addAttribute(ATTR_ERROR, SERVER_ERROR);
        model.addAttribute(ATTR_MESSAGE, SERVER_ERROR_DEFAULT_MESSAGE);
        return ERROR_PAGE;
    }


}
