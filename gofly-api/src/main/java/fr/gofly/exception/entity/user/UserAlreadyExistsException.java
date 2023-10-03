package fr.gofly.exception.entity.user;


/**
 * Custom exception class to represent the case when a user already exists in the database.
 */
public class UserAlreadyExistsException extends RuntimeException{

    /**
     * Constructs a new UserAlreadyExistsException with a message indicating that the given email address is already in use.
     *
     * @param userEmail The email address that is already in use.
     */
    public UserAlreadyExistsException(String userEmail){
        super("The email address " + userEmail + " is already in use!");
    }
}
