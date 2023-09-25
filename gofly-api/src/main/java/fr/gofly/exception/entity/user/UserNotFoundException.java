package fr.gofly.exception.entity.user;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String userEmail){
        super("Could not find user with the UUID " + userEmail + " !");
    }
}
