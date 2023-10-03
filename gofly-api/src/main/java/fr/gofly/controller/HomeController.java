package fr.gofly.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HomeController {

    @GetMapping("/home")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("hello");
    }

}