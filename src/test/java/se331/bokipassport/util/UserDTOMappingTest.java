package se331.bokipassport.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import se331.bokipassport.entity.Level;
import se331.bokipassport.entity.User;
import se331.bokipassport.entity.dto.UserDTO;
import se331.bokipassport.security.user.Role;
import se331.bokipassport.security.user.UserAccount;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void theDtoKeepsTheEnabledFieldAndTheIsActiveJsonName() {
        UserDTO dto = LabMapper.INSTANCE.getUserDto(account(true));

        assertEquals(true, dto.getEnabled());
    }

    @Test
    void theJsonKeepsIsActiveAndCarriesNoForeignKeyForTheLevel() throws Exception {
        UserDTO dto = LabMapper.INSTANCE.getUserDto(account(false,
                Level.builder().id(1L).name("Level 1").build(),
                Level.builder().id(3L).name("Level 3").build()));
        String json = new ObjectMapper().writeValueAsString(dto);

        assertTrue(json.contains("\"isActive\":false"), "the JSON key must stay isActive");
        assertFalse(json.contains("\"enabled\""), "the field name must not leak into the JSON");
        assertFalse(json.contains("levelId"), "the level comes from the nested levels list, not a foreign key");
    }

    @Test
    void theLevelIsCarriedByTheNestedLevelsList() {
        UserDTO dto = LabMapper.INSTANCE.getUserDto(account(true,
                Level.builder().id(1L).name("Level 1").build(),
                Level.builder().id(3L).name("Level 3").build()));

        assertEquals(2, dto.getLevels().size());
        assertEquals(3L, dto.getLevels().get(1).getId());
    }

    @Test
    void theListMappingKeepsTheSameShape() {
        List<UserDTO> dtos = LabMapper.INSTANCE.getUserDto(
                List.of(account(false, Level.builder().id(2L).name("Level 2").build())));

        assertEquals(false, dtos.get(0).getEnabled());
        assertEquals(1, dtos.get(0).getLevels().size());
    }
}
