package fr.gofly.exception.advice.entity;

import fr.gofly.exception.entity.user.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserAlreadyExistsAdvice {
    @ResponseBody
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String employeeNotFoundHandler(UserAlreadyExistsException ex) {
        return ex.getMessage();
    }
}
