package fr.gofly.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WhoAmI {
    @GetMapping("/api/whoami")
    public ResponseEntity<String> whoAmI() {
        //TODO : Retourner les informations de l'utilisateur authentifié
        return null;
    }
}
