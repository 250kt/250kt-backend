package fr.gofly.exception.entity.user;

/**
 * Custom exception class to represent the case when a user is not found in the system.
 */
public class UserNotFoundException extends RuntimeException{

    /**
     * Constructs a new UserNotFoundException with a message indicating that no user with the given UUID was found.
     *
     * @param userId The UUID of the user that could not be found.
     */
    public UserNotFoundException(String userId){
        super("Could not find user with the UUID " + userId + "!");
    }
}
