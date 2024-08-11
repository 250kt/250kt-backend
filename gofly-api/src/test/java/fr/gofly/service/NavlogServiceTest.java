package fr.gofly.service;

import fr.gofly.helper.NavlogHelper;
import fr.gofly.helper.UserHelper;
import fr.gofly.model.*;
import fr.gofly.repository.NavlogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NavlogServiceTest {
    @InjectMocks
    private NavlogService navlogService;

    @Mock
    private NavlogRepository navlogRepository;


    @Mock
    private NavlogHelper navlogHelper;

    @Mock
    private UserHelper userHelper;

    @Test
    void testCreateNavlog_ShouldReturnOptionalEmpty_WhenIsMissingMandatoryFields(){
        Navlog navlog = Navlog.builder()
                .id(1)
                .user(new User())
                .aircraft(new Aircraft())
                .build();

        when(navlogHelper.isMissingMandatoryFields(navlog)).thenReturn(true);

        assertEquals(Optional.empty(), navlogService.createNavlog(navlog));
    }

    @Test
    void testRetrieveNavlog_shouldReturnOptionalEmpty_WhenNavlogNotFound(){
        User user = User.builder().build();

        Navlog navlog = Navlog.builder()
                .id(1)
                .build();

        when(navlogRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertEquals(Optional.empty(), navlogService.retrieveNavlog(navlog.getId(), user));
    }

    @Test
    void testRetrieveNavlog_shouldReturnOptionalEmpty_WhenUserNotOwnerOrNotAnAdmin(){
        User user = User.builder().build();

        Navlog navlog = Navlog.builder()
                .id(1)
                .build();

        when(navlogRepository.findById(anyLong())).thenReturn(Optional.of(new Navlog()));
        when(navlogHelper.isNavlogOwnedByUser(any(Navlog.class), any(User.class))).thenReturn(false);

        assertEquals(Optional.empty(), navlogService.retrieveNavlog(navlog.getId(), user));
    }

    @Test
    void testDeleteNavlog_shouldReturnOptionalEmpty_WhenUserNotOwnerOrNotAnAdmin(){
        User user = User.builder()
                .build();

        Navlog navlog = Navlog.builder()
                .id(1)
                .build();

        when(navlogHelper.isNavlogOwnedByUser(any(Navlog.class), any(User.class))).thenReturn(false);

        assertFalse(navlogService.deleteNavlog(navlog, user));
    }

    @Test
    void testUpdateNavlog_shouldReturnOptionalEmpty_WhenUserNotOwnerOrNotAnAdmin_OrMissingMandatoryFields(){
        User user = User.builder().build();

        Navlog navlog = Navlog.builder()
                .id(1)
                .build();

        when(navlogHelper.isMissingMandatoryFields(any(Navlog.class))).thenReturn(true);

        assertEquals(Optional.empty(), navlogService.updateNavlog(navlog, user));
    }
}
