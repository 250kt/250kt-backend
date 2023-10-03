package fr.gofly;

import fr.gofly.model.Role;
import fr.gofly.model.User;
import fr.gofly.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APIFixtures implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        createUser();
    }

    private void createUser(){
        User user = new User();
        user.setUserEmail("valentin.delobel@outlook.fr");
        user.setUserPassword("T€st31400*");
        user.setUserRole(Role.ADMIN);
        user.setUserName("AureSkum");

        // userRepository.save(user);
    }
}
