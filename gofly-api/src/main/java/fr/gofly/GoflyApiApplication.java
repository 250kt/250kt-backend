package fr.gofly;

import fr.gofly.model.Role;
import fr.gofly.model.User;
import fr.gofly.repository.AirfieldRepository;
import fr.gofly.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GoflyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoflyApiApplication.class, args);
    }

}
