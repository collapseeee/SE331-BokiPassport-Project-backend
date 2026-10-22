package se331.bokipassport.security.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;
import se331.bokipassport.entity.Level;
import se331.bokipassport.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CurrentUserServiceTest {

    private static Level level(long id) {
        return Level.builder().id(id).name("Level " + id).build();
    }

    private static UserAccount account(List<Role> roles, Level... levels) {
        return UserAccount.builder()
                .username("boki")
                .enabled(true)
                .roles(roles)
                .profile(User.builder()
                        .id(2L)
                        .levels(new ArrayList<>(List.of(levels)))
                        .build())
                .build();
    }

    private static CurrentUserService serviceFor(UserAccount account) {
        UserAccountRepository repository = mock(UserAccountRepository.class);
        when(repository.findByUsername("boki")).thenReturn(Optional.ofNullable(account));
        return new CurrentUserService(repository);
    }

    private static void authenticateAs(String username) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(username, null));
    }

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void levelIdIsTheHighestLevelTheUserHolds() {
        authenticateAs("boki");
        CurrentUserService service = serviceFor(account(List.of(Role.ROLE_USER), level(1), level(3)));

        assertEquals(3L, service.getCurrentLevelId());
    }

    @Test
    void levelIdIsZeroWhenTheUserHoldsNoLevels() {
        authenticateAs("boki");
        CurrentUserService service = serviceFor(account(List.of(Role.ROLE_USER)));

        assertEquals(0L, service.getCurrentLevelId());
    }

    @Test
    void isAdminIsTrueOnlyForRoleAdmin() {
        authenticateAs("boki");
        assertTrue(serviceFor(account(List.of(Role.ROLE_ADMIN, Role.ROLE_USER))).isAdmin());
        assertFalse(serviceFor(account(List.of(Role.ROLE_USER))).isAdmin());
    }

    @Test
    void getCurrentUserThrowsWhenNobodyIsAuthenticated() {
        ResponseStatusException thrown = assertThrows(ResponseStatusException.class,
                () -> serviceFor(account(List.of(Role.ROLE_USER))).getCurrentUser());

        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatusCode());
    }

    @Test
    void getCurrentUserThrowsWhenTheAccountIsMissing() {
        authenticateAs("boki");
        CurrentUserService service = serviceFor(null);

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, service::getCurrentUser);

        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatusCode());
    }
}
