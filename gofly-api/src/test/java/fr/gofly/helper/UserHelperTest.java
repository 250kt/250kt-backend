package fr.gofly.helper;

import fr.gofly.model.Authority;
import fr.gofly.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserHelperTest {
    @InjectMocks
    private UserHelper userHelper;

    private List<Authority> authorities = new ArrayList<>();

    @BeforeEach
    void setUp(){
        authorities.add(Authority.BUDDING_PILOT);
    }

    @Test
    void testIsOwner_ShouldReturnTrue_WhenUserIsTheOwnerOfTheUserChecked(){
        User user = User.builder()
                .id("id")
                .email("test@250kt.com")
                .authorities(authorities)
                .build();

        String id = "id";

        assertTrue(userHelper.isOwner(user, id));
    }

    @Test
    void testIsOwner_ShouldReturnFalse_WhenTheUserIsNotTheOwnerOfTheUserChecked(){
        User user = User.builder()
                .id("id")
                .email("test@250kt.com")
                .authorities(authorities)
                .build();

        String id = "id2";

        assertFalse(userHelper.isOwner(user, id));
    }

    @Test
    void testIsAdmin_ShouldReturnTrue_WhenUserIsAdmin(){
        List<Authority> authority = new ArrayList<>();
        authority.add(Authority.ADMIN);

        User userAdmin = User.builder()
                .id("id")
                .email("test@250kt.com")
                .authorities(authority)
                .build();

        assertTrue(userHelper.isAdmin(userAdmin));
    }

    @Test
    void testIsAdmin_ShouldReturnFalse_WhenUserIsNotAdmin(){
        List<Authority> authority = new ArrayList<>();
        authority.add(Authority.BUDDING_PILOT);

        User userBuddingPilot = User.builder()
                .id("id")
                .email("test@250kt.com")
                .authorities(authority)
                .build();

        assertFalse(userHelper.isAdmin(userBuddingPilot));
    }

    @Test
    void testIsOwnerOrAdmin_ShouldReturnTrue_WhenUserIsOwner(){
        User user = User.builder()
                .id("id")
                .email("test@250kt.com")
                .authorities(authorities)
                .build();

        String id = "id";

        assertTrue(userHelper.isOwnerOrAdmin(user, id));
    }

    @Test
    void testIsOwnerOrAdmin_ShouldReturnTrue_WhenUserIsAdmin(){
        List<Authority> authority = new ArrayList<>();
        authority.add(Authority.ADMIN);

        User userAdmin = User.builder()
                .id("id")
                .email("test@250kt.com")
                .authorities(authority)
                .build();

        String id = "id";

        assertTrue(userHelper.isOwnerOrAdmin(userAdmin, id));
    }

    @Test
    void testIsOwnerOrAdmin_ShouldReturnFalse_WhenUserIsNotAdmin_WhenUserNotOwner(){
        List<Authority> authority = new ArrayList<>();
        authority.add(Authority.BUDDING_PILOT);

        User userBuddingPilot = User.builder()
                .id("id")
                .email("test@250kt.com")
                .authorities(authority)
                .build();

        String id = "id2";

        assertFalse(userHelper.isOwnerOrAdmin(userBuddingPilot, id));
    }
}
