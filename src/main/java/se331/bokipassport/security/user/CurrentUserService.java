package se331.bokipassport.security.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se331.bokipassport.entity.Level;
import se331.bokipassport.entity.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrentUserService {
    private final UserAccountRepository userAccountRepository;

    public UserAccount getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null
                || "anonymousUser".equals(authentication.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated");
        }
        return userAccountRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated"));
    }

    public Long getCurrentLevelId() {
        List<Level> levels = getCurrentProfile().getLevels();
        if (levels == null) {
            return 0L;
        }
        return levels.stream()
                .map(Level::getId)
                .max(Long::compareTo)
                .orElse(0L);
    }

    public User getCurrentProfile() {
        User profile = getCurrentUser().getProfile();
        if (profile == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Account has no profile");
        }
        return profile;
    }

    public boolean isAdmin() {
        return getCurrentUser().getRoles().contains(Role.ROLE_ADMIN);
    }
}
