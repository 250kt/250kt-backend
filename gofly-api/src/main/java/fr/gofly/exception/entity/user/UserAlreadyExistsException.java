package fr.gofly.exception.entity.user;


public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String userEmail){
        super("The email address " + userEmail + " is already used !");
    }
}
