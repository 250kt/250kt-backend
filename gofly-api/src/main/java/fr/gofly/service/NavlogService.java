package fr.gofly.service;

import fr.gofly.helper.NavlogHelper;
import fr.gofly.helper.UserHelper;
import fr.gofly.model.Navlog;
import fr.gofly.model.User;
import fr.gofly.repository.NavlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service class for managing navigation logs (Navlogs).
 */
@Service
@RequiredArgsConstructor
public class NavlogService {
    private final NavlogRepository navlogRepository;
    private final NavlogHelper navlogHelper;
    private final UserHelper userHelper;

    /**
     * Create a new navigation log (Navlog).
     *
     * @param navlog The Navlog to be created.
     * @param user The ower of the Navlog to be created.
     * @return An Optional containing the created Navlog, or empty if mandatory fields are missing.
     */
    public Optional<Navlog> createNavlog(Navlog navlog, User user){
        navlog.setUser(user);
        if(navlogHelper.isMissingMandatoryFields(navlog))
            return Optional.empty();

        return Optional.of(navlogRepository.save(navlog));
    }

    /**
     * Retrieve a specific navigation log (Navlog) based on its ID.
     *
     * @param navlogId The ID of the Navlog to retrieve.
     * @param user The user requesting the retrieval.
     * @return An Optional containing the retrieved Navlog, or empty if it doesn't exist, or if the user doesn't have the necessary permissions.
     */
    public Optional<Navlog> retrieveNavlog(long navlogId, User user){
        Optional<Navlog> navlogOptional = navlogRepository.findById(navlogId);

        if(navlogOptional.isEmpty())
            return Optional.empty();

        if(!navlogHelper.isNavlogOwnedByUser(navlogOptional.get(), user) || !userHelper.isAdmin(user))
            return Optional.empty();

        return navlogOptional;
    }

    /**
     * Retrieve all navigation logs (Navlogs) for a specific user.
     *
     * @param user The user for whom to retrieve Navlogs.
     * @return A set of Navlogs associated with the user.
     */
    public Set<Navlog> retrieveNavlogs(User user){
        return navlogRepository.findAllByUserId(user.getId());
    }

    /**
     * Retrieve all navigation logs (Navlogs).
     *
     * @return A list of all available Navlogs.
     */
    public List<Navlog> retrieveAllNavlogs(){
        return navlogRepository.findAll();
    }

    /**
     * Delete a specific navigation log (Navlog).
     *
     * @param navlog The Navlog to be deleted.
     * @param user The user requesting the deletion.
     * @return True if the deletion was successful, false otherwise (e.g., missing permissions).
     */
    public boolean deleteNavlog(Navlog navlog, User user){
        if(!navlogHelper.isNavlogOwnedByUser(navlog, user) || !userHelper.isAdmin(user))
            return false;

        navlogRepository.delete(navlog);
        return true;
    }

    /**
     * Update an existing navigation log (Navlog).
     *
     * @param navlog The Navlog to be updated.
     * @param user The user requesting the update.
     * @return An Optional containing the updated Navlog, or empty if mandatory fields are missing, or if the user doesn't have the necessary permissions.
     */
    public Optional<Navlog> updateNavlog(Navlog navlog, User user){
        if(navlogHelper.isMissingMandatoryFields(navlog) ||
           !navlogHelper.isNavlogOwnedByUser(navlog, user) ||
           !userHelper.isAdmin(user))
            return Optional.empty();

        return Optional.of(navlogRepository.save(navlog));
    }
}
