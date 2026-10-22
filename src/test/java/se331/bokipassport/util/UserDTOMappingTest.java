package se331.bokipassport.util;

import org.junit.jupiter.api.Test;
import se331.bokipassport.entity.Level;
import se331.bokipassport.entity.User;
import se331.bokipassport.entity.dto.UserDTO;
import se331.bokipassport.security.user.Role;
import se331.bokipassport.security.user.UserAccount;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDTOMappingTest {

    private static UserAccount account(boolean enabled, Level... levels) {
        return UserAccount.builder()
                .username("boki")
                .enabled(enabled)
                .roles(List.of(Role.ROLE_USER))
                .profile(User.builder()
                        .id(2L)
                        .firstName("Boki")
                        .lastName("Chan")
                        .province("Chiang Rai")
                        .image("https://example.com/boki.png")
                        .levels(List.of(levels))
                        .badges(List.of())
                        .build())
                .build();
    }

    @Test
    void mapsIsActiveFromAccountEnabled() {
        UserDTO dto = LabMapper.INSTANCE.getUserDto(account(false));

        assertEquals(false, dto.getIsActive());
    }

    @Test
    void mapsLevelIdToTheHighestLevelTheUserUnlocked() {
        UserDTO dto = LabMapper.INSTANCE.getUserDto(account(true,
                Level.builder().id(1L).name("Level 1").build(),
                Level.builder().id(3L).name("Level 3").build()));

        assertEquals(3L, dto.getLevelId());
    }

    @Test
    void mapsIsActiveAndLevelIdInTheListMappingToo() {
        List<UserDTO> dtos = LabMapper.INSTANCE.getUserDto(
                List.of(account(false, Level.builder().id(2L).name("Level 2").build())));

        assertEquals(false, dtos.get(0).getIsActive());
        assertEquals(2L, dtos.get(0).getLevelId());
    }
}
