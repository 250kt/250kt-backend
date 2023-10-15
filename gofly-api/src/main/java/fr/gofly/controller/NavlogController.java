package fr.gofly.controller;

import fr.gofly.helper.NavlogHelper;
import fr.gofly.helper.UserHelper;
import fr.gofly.model.Navlog;
import fr.gofly.model.User;
import fr.gofly.repository.NavlogRepository;
import fr.gofly.service.NavlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/navlog")
@RequiredArgsConstructor
public class NavlogController {
    private final NavlogService navlogService;

    @GetMapping("/{navlogId}")
    public ResponseEntity<Navlog> retrieveNavlog(@AuthenticationPrincipal User userAuthenticated, @PathVariable long navlogId){
        Optional<Navlog> navlogOptional = navlogService.retrieveNavlog(navlogId, userAuthenticated);
        return navlogOptional.map(navlog -> new ResponseEntity<>(navlog, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping
    public ResponseEntity<Set<Navlog>> retrieveNavlogs(@AuthenticationPrincipal User userAuthenticated){
        Set<Navlog> navlogs = navlogService.retrieveNavlogs(userAuthenticated);
        return new ResponseEntity<>(navlogs, HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Navlog>> retrieveAllNavlogs(){
        List<Navlog> navlogs = navlogService.retrieveAllNavlogs();
        return new ResponseEntity<>(navlogs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Navlog> createNavlog(@RequestBody Navlog navlog){
        Optional<Navlog> navlogOptional = navlogService.createNavlog(navlog);
        return navlogOptional.map(navlogV -> new ResponseEntity<>(navlogV, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PutMapping
    ResponseEntity<Navlog> updateNavlog(@AuthenticationPrincipal User userAuthenticated, @RequestBody Navlog navlog) {
        Optional<Navlog> navlogOptional = navlogService.updateNavlog(navlog, userAuthenticated);
        return navlogOptional.map(navlogV -> new ResponseEntity<>(navlogV, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping
    ResponseEntity<HttpStatus> deleteNavlog(@AuthenticationPrincipal User userAuthenticated, @RequestBody Navlog navlog) {
        return navlogService.deleteNavlog(navlog, userAuthenticated) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
