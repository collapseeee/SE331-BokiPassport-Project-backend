package se331.bokipassport.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se331.bokipassport.entity.Level;
import se331.bokipassport.entity.User;
import se331.bokipassport.repository.LevelRepository;
import se331.bokipassport.repository.UserRepository;
import se331.bokipassport.security.user.Role;
import se331.bokipassport.security.user.UserAccount;
import se331.bokipassport.security.user.UserAccountRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitApp implements ApplicationListener<ApplicationReadyEvent> {

    final UserRepository userRepository;
    final UserAccountRepository userAccountRepository;
    final LevelRepository levelRepository;
    /* Repository variables here */

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent application) {
        /* Declare levels */
        Level level1;
        Level level2;
        Level level3;
        Level level4;
        level1 = levelRepository.save(Level.builder()
                .name("Level 1")
                .description("Beginner")
                .passCriteria(0.5)
                .build());
        level2 = levelRepository.save(Level.builder()
                .name("Level 2")
                .description("Intermediate")
                .passCriteria(0.55)
                .build());
        level3 = levelRepository.save(Level.builder()
                .name("Level 3")
                .description("Advanced")
                .passCriteria(0.75)
                .build());
        level4 = levelRepository.save(Level.builder()
                .name("Level 4")
                .description("Master")
                .passCriteria(0.8)
                .build());

        /* Declare users and user accounts*/
        User user1;
        User user2;
        User user3;
        User user4;
        User user5;
        User user6;
        User user7;
        User user8;
        User user9;
        User user10;
        User user11;
        User user12;
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        userAccountRepository.save(UserAccount.builder()
                .username("admin")
                .password(encoder.encode("123"))
                .enabled(true)
                .roles(List.of(Role.ROLE_ADMIN, Role.ROLE_USER))
                .build());

        user1 = userRepository.save(User.builder()
                .firstName("Boki")
                .lastName("Chan")
                .province("Chiang Rai")
                .image("https://avatars.githubusercontent.com/u/316038766?s=200&v=4")
                .levels(List.of(level1))
                .build());
        userAccountRepository.save(UserAccount.builder()
                .username("boki")
                .password(encoder.encode("boki123"))
                .profile(user1)
                .enabled(true)
                .roles(List.of(Role.ROLE_USER))
                .build());

        user2 = userRepository.save(User.builder()
                .firstName("Gerry")
                .lastName("Express")
                .province("Chiang Kai")
                .image("https://avatars.githubusercontent.com/u/99584671?s=200&v=4")
                .levels(List.of(level1, level2, level3))
                .build());
        userAccountRepository.save(UserAccount.builder()
                .username("gerry")
                .password(encoder.encode("123"))
                .profile(user2)
                .enabled(false)
                .roles(List.of(Role.ROLE_USER))
                .build());

        user3 = userRepository.save(User.builder()
                .firstName("Collapsee")
                .lastName("Chan")
                .province("Chiang Mai")
                .image("https://avatars.githubusercontent.com/u/134732932?s=200&v=4")
                .levels(List.of(level1, level2))
                .build());
        userAccountRepository.save(UserAccount.builder()
                .username("collapsee")
                .password(encoder.encode("123"))
                .profile(user3)
                .enabled(true)
                .roles(List.of(Role.ROLE_USER))
                .build());

        user4 = userRepository.save(User.builder()
                .firstName("Somchai")
                .lastName("Jaidee")
                .province("Chiang Mai")
                .image("https://picsum.photos/seed/somchai/200")
                .levels(List.of(level1, level2))
                .build());
        userAccountRepository.save(UserAccount.builder()
                .username("somchai")
                .password(encoder.encode("123"))
                .profile(user4)
                .enabled(true)
                .roles(List.of(Role.ROLE_USER))
                .build());

        user5 = userRepository.save(User.builder()
                .firstName("Mali")
                .lastName("Sooksabai")
                .province("Bangkok")
                .image("https://picsum.photos/seed/mali/200")
                .levels(List.of(level1))
                .build());
        userAccountRepository.save(UserAccount.builder()
                .username("mali")
                .password(encoder.encode("123"))
                .profile(user5)
                .enabled(true)
                .roles(List.of(Role.ROLE_USER))
                .build());

        user6 = userRepository.save(User.builder()
                .firstName("Arthit")
                .lastName("Rungroj")
                .province("Khon Kaen")
                .image("https://picsum.photos/seed/arthit/200")
                .levels(List.of(level1, level2, level3))
                .build());
        userAccountRepository.save(UserAccount.builder()
                .username("arthit")
                .password(encoder.encode("123"))
                .profile(user6)
                .enabled(false)
                .roles(List.of(Role.ROLE_USER))
                .build());

        user7 = userRepository.save(User.builder()
                .firstName("Nicha")
                .lastName("Wongsuwan")
                .province("Phuket")
                .image("https://picsum.photos/seed/nicha/200")
                .levels(List.of(level1, level2, level3))
                .build());
        userAccountRepository.save(UserAccount.builder()
                .username("nicha")
                .password(encoder.encode("123"))
                .profile(user7)
                .enabled(true)
                .roles(List.of(Role.ROLE_USER))
                .build());

        user8 = userRepository.save(User.builder()
                .firstName("Krittin")
                .lastName("Sricharan")
                .province("Chiang Mai")
                .image("https://picsum.photos/seed/krittin/200")
                .levels(List.of(level1, level2, level3))
                .build());
        userAccountRepository.save(UserAccount.builder()
                .username("krittin")
                .password(encoder.encode("123"))
                .profile(user8)
                .enabled(true)
                .roles(List.of(Role.ROLE_USER))
                .build());

        user9 = userRepository.save(User.builder()
                .firstName("Pimchanok")
                .lastName("Klinhom")
                .province("Bangkok")
                .image("https://picsum.photos/seed/pimchanok/200")
                .levels(List.of(level1, level2, level3, level4))
                .build());
        userAccountRepository.save(UserAccount.builder()
                .username("pimchanok")
                .password(encoder.encode("123"))
                .profile(user9)
                .enabled(true)
                .roles(List.of(Role.ROLE_USER))
                .build());

        user10 = userRepository.save(User.builder()
                .firstName("Tanapat")
                .lastName("Kongkaew")
                .province("Chiang Rai")
                .image("https://picsum.photos/seed/tanapat/200")
                .levels(List.of(level1, level2, level3))
                .build());
        userAccountRepository.save(UserAccount.builder()
                .username("tanapat")
                .password(encoder.encode("123"))
                .profile(user10)
                .enabled(true)
                .roles(List.of(Role.ROLE_USER))
                .build());

        user11 = userRepository.save(User.builder()
                .firstName("Supansa")
                .lastName("Boonyarit")
                .province("Chonburi")
                .image("https://picsum.photos/seed/supansa/200")
                .levels(List.of(level1))
                .build());
        userAccountRepository.save(UserAccount.builder()
                .username("supansa")
                .password(encoder.encode("123"))
                .profile(user11)
                .enabled(false)
                .roles(List.of(Role.ROLE_USER))
                .build());

        user12 = userRepository.save(User.builder()
                .firstName("Thianchai")
                .lastName("Jaroenporn")
                .province("Chiang Mai")
                .image("https://picsum.photos/seed/Thianchai/200")
                .levels(List.of(level1))
                .build());
        userAccountRepository.save(UserAccount.builder()
                .username("thianlnwza007")
                .password(encoder.encode("123"))
                .profile(user12)
                .enabled(true)
                .roles(List.of(Role.ROLE_USER))
                .build());
    }
}
