package fr.gofly.exception.advice.entity;

import fr.gofly.exception.entity.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Advice class to handle exceptions related to user not found.
 */
@ControllerAdvice
public class UserNotFoundAdvice {
    /**
     * Handles exceptions of type UserNotFoundException.
     *
     * @param ex The UserNotFoundException to be handled.
     * @return A message describing the exception.
     */
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(UserNotFoundException ex) {
        return ex.getMessage();
    }
}
