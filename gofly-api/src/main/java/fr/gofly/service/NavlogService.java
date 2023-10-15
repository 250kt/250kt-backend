package fr.gofly.service;

import fr.gofly.helper.NavlogHelper;
import fr.gofly.helper.UserHelper;
import fr.gofly.model.Navlog;
import fr.gofly.model.User;
import fr.gofly.repository.NavlogRepository;
import fr.gofly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class NavlogService {
    private final NavlogRepository navlogRepository;
    private final NavlogHelper navlogHelper;
    private final UserHelper userHelper;

    public Optional<Navlog> createNavlog(Navlog navlog){
        if(navlogHelper.isMissingMandatoryFields(navlog))
            return Optional.empty();

        return Optional.of(navlogRepository.save(navlog));
    }

    public Optional<Navlog> retrieveNavlog(long navlogId, User user){
        Optional<Navlog> navlogOptional = navlogRepository.findById(navlogId);

        if(navlogOptional.isEmpty())
            return Optional.empty();

        if(!navlogHelper.isNavlogOwnedByUser(navlogOptional.get(), user) || !userHelper.isAdmin(user))
            return Optional.empty();

        return navlogOptional;
    }

    public Set<Navlog> retrieveNavlogs(User user){
        return navlogRepository.findAllByUserId(user.getId());
    }

    public List<Navlog> retrieveAllNavlogs(){
        return navlogRepository.findAll();
    }

    public boolean deleteNavlog(Navlog navlog, User user){
        if(!navlogHelper.isNavlogOwnedByUser(navlog, user) || !userHelper.isAdmin(user))
            return false;

        navlogRepository.delete(navlog);
        return true;
    }

    public Optional<Navlog> updateNavlog(Navlog navlog, User user){
        if(navlogHelper.isMissingMandatoryFields(navlog) ||
           !navlogHelper.isNavlogOwnedByUser(navlog, user) ||
           !userHelper.isAdmin(user))
            return Optional.empty();

        return Optional.of(navlogRepository.save(navlog));
    }
}
