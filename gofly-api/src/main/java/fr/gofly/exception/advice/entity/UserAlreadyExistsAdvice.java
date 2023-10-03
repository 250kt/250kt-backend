package fr.gofly.exception.advice.entity;

import fr.gofly.exception.entity.user.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Advice class to handle exceptions related to user already existing.
 */
@ControllerAdvice
public class UserAlreadyExistsAdvice {
    /**
     * Handles exceptions of type UserAlreadyExistsException.
     *
     * @param ex The UserAlreadyExistsException to be handled.
     * @return A message describing the exception.
     */
    @ResponseBody
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String employeeNotFoundHandler(UserAlreadyExistsException ex) {
        return ex.getMessage();
    }
}
