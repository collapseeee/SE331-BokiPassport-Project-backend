package se331.bokipassport.util;

import org.springframework.security.core.Authentication;
import se331.bokipassport.entity.Level;
import se331.bokipassport.security.user.Role;

import java.util.List;

public final class ExamAccess {
    private ExamAccess() {
    }

    public static boolean canAccessExam(Long examLevelId, Long userLevelId, boolean isAdmin) {
        if (isAdmin) {
            return true;
        }
        if (examLevelId == null || userLevelId == null) {
            return false;
        }
        return examLevelId <= userLevelId;
    }

    /** The caller's level is the highest one they have unlocked. */
    public static Long highestLevelId(List<Level> levels) {
        if (levels == null || levels.isEmpty()) {
            return 0L;
        }
        return levels.stream()
                .map(Level::getId)
                .max(Long::compareTo)
                .orElse(0L);
    }

    /**
     * The role comes from the token's authorities, not from the account entity:
     * the account in the security context is detached, so its collections cannot be read.
     */
    public static boolean isAdmin(Authentication authentication) {
        if (authentication == null) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> Role.ROLE_ADMIN.name().equals(authority.getAuthority()));
    }
}
