package se331.bokipassport.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se331.bokipassport.entity.*;
import se331.bokipassport.repository.*;
import se331.bokipassport.security.user.Role;
import se331.bokipassport.security.user.UserAccount;
import se331.bokipassport.security.user.UserAccountRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitApp implements ApplicationListener<ApplicationReadyEvent> {

    final UserRepository userRepository;
    final UserAccountRepository userAccountRepository;
    final BenefitRepository benefitRepository;
    final LevelRepository levelRepository;
    final CourseRepository courseRepository;
    final BadgeRepository badgeRepository;
    final ExamRepository examRepository;
    final QuestionRepository questionRepository;
    final UserExamHistoryRepository userExamHistoryRepository;
    private final AnswerRepository answerRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent application) {

        /* Declare levels */
        Level level1;
        Level level2;
        Level level3;
        Level level4;

        level1 = Level.builder()
                .name("Level 1")
                .description("Beginner")
                .passCriteria(0.5)
                .build();

        level2 = Level.builder()
                .name("Level 2")
                .description("Intermediate")
                .passCriteria(0.55)
                .build();

        level3 = Level.builder()
                .name("Level 3")
                .description("Advanced")
                .passCriteria(0.75)
                .build();

        level4 = Level.builder()
                .name("Level 4")
                .description("Master")
                .passCriteria(0.8)
                .build();

        /* Declare benefits */

        benefitRepository.save(Benefit.builder()
                .description("You can use BokiAI up to 1M Tokens!")
                .level(level1)
                .build());
        benefitRepository.save(Benefit.builder()
                .description("You can generate image using BokiAI!")
                .level(level1)
                .build());
        benefitRepository.save(Benefit.builder()
                .description("You can use BokiAI up to 5M Tokens!")
                .level(level2)
                .build());
        benefitRepository.save(Benefit.builder()
                .description("You can generate image using BokiAI!")
                .level(level2)
                .build());
        benefitRepository.save(Benefit.builder()
                .description("You can talk with Boki!")
                .level(level2)
                .build());
        benefitRepository.save(Benefit.builder()
                .description("You can use BokiAI up to 5M Tokens!")
                .level(level3)
                .build());
        benefitRepository.save(Benefit.builder()
                .description("You can generate image using BokiAI!")
                .level(level3)
                .build());
        benefitRepository.save(Benefit.builder()
                .description("You can talk with Boki!")
                .level(level3)
                .build());
        benefitRepository.save(Benefit.builder()
                .description("You can let BokiAI to read and write the input files!")
                .level(level3)
                .build());
        benefitRepository.save(Benefit.builder()
                .description("You can use BokiAI up to 10M Tokens!")
                .level(level4)
                .build());
        benefitRepository.save(Benefit.builder()
                .description("You can generate image using BokiAI!")
                .level(level4)
                .build());
        benefitRepository.save(Benefit.builder()
                .description("You can talk with Boki!")
                .level(level4)
                .build());
        benefitRepository.save(Benefit.builder()
                .description("You can let BokiAI to read and write the input files!")
                .level(level4)
                .build());
        benefitRepository.save(Benefit.builder()
                .description("You can customize BokiAI model based on desired proficiency field.")
                .level(level4)
                .build());

        /* Declare exam */
        Exam exam1, exam2, exam3;
        exam1 = examRepository.save(Exam.builder()
                .name("Upgrade to Level 2 examination")
                .description("Introduction to BokiAI.")
                .level(level1)
                .build());
        exam2 = examRepository.save(Exam.builder()
                .name("Upgrade to Level 3 examination")
                .description("BokiAI design patterns.")
                .level(level2)
                .build());
        exam3 = examRepository.save(Exam.builder()
                .name("Upgrade to Level 4 examination")
                .description("3 Advanced BokiAI design.")
                .level(level3)
                .build());

        /* Declare questions */
        Question question1, question2, question3, question4, question5, question6, question7, question8, question9, question10, question11, question12, question13, question14, question15;
        question1 = Question.builder()
                .questionText("What is BokiAI?")
                .exam(exam1)
                .build();
        question2 = Question.builder()
                .questionText("How to use BokiAI?")
                .exam(exam1)
                .build();
        question3 = Question.builder()
                .questionText("Who own BokiAI?")
                .exam(exam1)
                .build();
        question4 = Question.builder()
                .questionText("Which of the following choices uses BokiAI inappropriately?")
                .exam(exam1)
                .build();
        question5 = Question.builder()
                .questionText("What is the incorrect way of using BokiAI?")
                .exam(exam1)
                .build();

        question6 = Question.builder()
                .questionText("What is design pattern?")
                .exam(exam2)
                .build();
        question7 = Question.builder()
                .questionText("Why BokiAI design pattern is different from other AI?")
                .exam(exam2)
                .build();
        question8 = Question.builder()
                .questionText("What are the advantage of using BokiAI?")
                .exam(exam2)
                .build();
        question9 = Question.builder()
                .questionText("When was the BokiNarak group established?")
                .exam(exam2)
                .build();
        question10 = Question.builder()
                .questionText("Imagine BokiAI did not function correctly, what would you do?")
                .exam(exam2)
                .build();

        question11 = Question.builder()
                .questionText("Why there are 3 different BokiAI advanced design patterns?")
                .exam(exam3)
                .build();
        question12 = Question.builder()
                .questionText("Who proposed 3 different BokiAI advanced design patterns?")
                .exam(exam3)
                .build();
        question13 = Question.builder()
                .questionText("How many 3 BokiAI advanced design patterns are there? And what are they?")
                .exam(exam3)
                .build();
        question14 = Question.builder()
                .questionText("What to do when BokiAI stopped working?")
                .exam(exam3)
                .build();
        question15 = Question.builder()
                .questionText("What should you ran out of BokiAI token?")
                .exam(exam3)
                .build();

        /* Declare answers*/
        // Question 1
        answerRepository.save(Answer.builder()
                .answerText("The real boki")
                .isCorrect(false)
                .question(question1)
                .build());
        answerRepository.save(Answer.builder()
                .answerText("The Agentic Artificial Inteligence")
                .isCorrect(true)
                .question(question1)
                .build());
        answerRepository.save(Answer.builder()
                .answerText("One of the BokiNarak Group member")
                .isCorrect(false)
                .question(question1)
                .build());
        answerRepository.save(Answer.builder()
                .answerText("953331 Component-Based Software Development")
                .isCorrect(false)
                .question(question1)
                .build());

        // Question 2
        answerRepository.save(Answer.builder()
                .answerText("By typing prompts into the chat interface")
                .isCorrect(true)
                .question(question2)
                .build());
        answerRepository.save(Answer.builder()
                .answerText("By inserting a physical USB drive into the computer")
                .isCorrect(false)
                .question(question2)
                .build());
        answerRepository.save(Answer.builder()
                .answerText("By pressing the power button twice")
                .isCorrect(false)
                .question(question2)
                .build());
        answerRepository.save(Answer.builder()
                .answerText("By calling the BokiNarak hotline service")
                .isCorrect(false)
                .question(question2)
                .build());

        // Question 3
        answerRepository.save(Answer.builder()
                .answerText("The BokiNarak Group")
                .isCorrect(true)
                .question(question3)
                .build());
        answerRepository.save(Answer.builder()
                .answerText("OpenAI Corporation")
                .isCorrect(false)
                .question(question3)
                .build());
        answerRepository.save(Answer.builder()
                .answerText("Google LLC")
                .isCorrect(false)
                .question(question3)
                .build());
        answerRepository.save(Answer.builder()
                .answerText("Microsoft Corporation")
                .isCorrect(false)
                .question(question3)
                .build());

        // Question 4
        answerRepository.save(Answer.builder().
                answerText("Using BokiAI to brainstorm ideas for a software project")
                .isCorrect(false)
                .question(question4)
                .build());
        answerRepository.save(Answer.builder().
                answerText("Using BokiAI to debug code components")
                .isCorrect(false)
                .question(question4)
                .build());
        answerRepository.save(Answer.builder().
                answerText("Using BokiAI to generate unauthorized malware to hack systems")
                .isCorrect(true)
                .question(question4)
                .build());
        answerRepository.save(Answer.builder().
                answerText("Using BokiAI to summarize meeting notes")
                .isCorrect(false)
                .question(question4)
                .build());

        // Question 5
        answerRepository.save(Answer.builder().
                answerText("Using BokiAI to generate malicious phishing emails")
                .isCorrect(true)
                .question(question5)
                .build());
        answerRepository.save(Answer.builder().
                answerText("Using BokiAI to help write software documentation")
                .isCorrect(false)
                .question(question5)
                .build());
        answerRepository.save(Answer.builder().
                answerText("Using BokiAI to explain complex component-based architecture concepts")
                .isCorrect(false)
                .question(question5)
                .build());
        answerRepository.save(Answer.builder().
                answerText("Using BokiAI to assist in refactoring code for better performance")
                .isCorrect(false)
                .question(question5)
                .build());

        // Question 6
        answerRepository.save(Answer.builder().
                answerText("A blueprint for code that is definitely not copied from StackOverflow")
                .isCorrect(true)
                .question(question6)
                .build());
        answerRepository.save(Answer.builder().
                answerText("The secret recipe of Mama instant noodles")
                .isCorrect(false)
                .question(question6)
                .build());
        answerRepository.save(Answer.builder().
                answerText("A fashion styling guide for oversized streetwear")
                .isCorrect(false)
                .question(question6)
                .build());
        answerRepository.save(Answer.builder().
                answerText("A random layout you draw when you are sleepy in class")
                .isCorrect(false)
                .question(question6)
                .build());

        // Question 7
        answerRepository.save(Answer.builder().
                answerText("It can talk back and roast your spaghetti code like a true friend")
                .isCorrect(true)
                .question(question7)
                .build());
        answerRepository.save(Answer.builder().
                answerText("It requires you to sacrifice 5 baht to the computer shrine")
                .isCorrect(false)
                .question(question7)
                .build());
        answerRepository.save(Answer.builder().
                answerText("It automatically passes all your midterms without studying")
                .isCorrect(false)
                .question(question7)
                .build());
        answerRepository.save(Answer.builder().
                answerText("It runs on pure iced Thai milk tea energy")
                .isCorrect(false)
                .question(question7)
                .build());

        // Question 8
        answerRepository.save(Answer.builder().
                answerText("Saves you from pulling an all-nighter before the project deadline")
                .isCorrect(true)
                .question(question8)
                .build());
        answerRepository.save(Answer.builder().
                answerText("Instantly boosts your Wi-Fi speed to 10Gbps")
                .isCorrect(false)
                .question(question8)
                .build());
        answerRepository.save(Answer.builder().
                answerText("Cooks instant noodles for you while compiling")
                .isCorrect(false)
                .question(question8)
                .build());
        answerRepository.save(Answer.builder().
                answerText("Pays your university tuition fees automatically")
                .isCorrect(false)
                .question(question8)
                .build());

        // Question 9
        answerRepository.save(Answer.builder().
                answerText("When the group members finally woke up and decided to do work")
                .isCorrect(true)
                .question(question9)
                .build());
        answerRepository.save(Answer.builder().
                answerText("During King Rama V's reign")
                .isCorrect(false)
                .question(question9)
                .build());
        answerRepository.save(Answer.builder().
                answerText("Right after everyone finished their last can of M-150 energy drink")
                .isCorrect(false)
                .question(question9)
                .build());
        answerRepository.save(Answer.builder().
                answerText("Yesterday at 3 AM when panic set in")
                .isCorrect(false)
                .question(question9)
                .build());

        // Question 10
        answerRepository.save(Answer.builder().
                answerText("Blame your teammate and pretend your internet connection dropped")
                .isCorrect(true)
                .question(question10)
                .build());
        answerRepository.save(Answer.builder().
                answerText("Perform a sacred ritual dance to appease the server gods")
                .isCorrect(false)
                .question(question10)
                .build());
        answerRepository.save(Answer.builder().
                answerText("Throw your laptop out the second-floor dorm window")
                .isCorrect(false)
                .question(question10)
                .build());
        answerRepository.save(Answer.builder().
                answerText("Cry softly while waiting for the professor's extension")
                .isCorrect(false)
                .question(question10)
                .build());

        // Question 11
        answerRepository.save(Answer.builder().
                answerText("Because one pattern is never enough for a chaotic software project")
                .isCorrect(true)
                .question(question11)
                .build());
        answerRepository.save(Answer.builder().
                answerText("To match the 3 distinct flavors of Mama noodles in your room")
                .isCorrect(false)
                .question(question11)
                .build());
        answerRepository.save(Answer.builder().
                answerText("To confuse freshmen during the software engineering final exam")
                .isCorrect(false)
                .question(question11)
                .build());
        answerRepository.save(Answer.builder().
                answerText("Just for fun, like having 3 different group chats for the same assignment")
                .isCorrect(false)
                .question(question11)
                .build());

        // Question 12
        answerRepository.save(Answer.builder().
                answerText("The legendary BokiNarak senior who hadn't slept in 4 days")
                .isCorrect(true)
                .question(question12)
                .build());
        answerRepository.save(Answer.builder().
                answerText("Uncle down the soi who fixes motorbikes")
                .isCorrect(false)
                .question(question12)
                .build());
        answerRepository.save(Answer.builder().
                answerText("A stray cat hanging around the computer science building")
                .isCorrect(false)
                .question(question12)
                .build());
        answerRepository.save(Answer.builder().
                answerText("Your mom when she asks why you are staring at a blank screen")
                .isCorrect(false)
                .question(question12)
                .build());

        // Question 13
        answerRepository.save(Answer.builder().
                answerText("There are 3: Copy, Paste, and Pray")
                .isCorrect(true)
                .question(question13)
                .build());
        answerRepository.save(Answer.builder().
                answerText("There are 3: Sleep, Eat, and Repeat")
                .isCorrect(false)
                .question(question13)
                .build());
        answerRepository.save(Answer.builder().
                answerText("There are 3: Bug, Crash, and Fix")
                .isCorrect(false)
                .question(question13)
                .build());
        answerRepository.save(Answer.builder().
                answerText("There are 3: HTML, CSS, and pure despair")
                .isCorrect(false)
                .question(question13)
                .build());

        // Question 14
        answerRepository.save(Answer.builder().
                answerText("Close laptop, go buy grilled pork skewers (moo ping), and sleep")
                .isCorrect(true)
                .question(question14).build());
        answerRepository.save(Answer.builder().
                answerText("Scream loudly until the code magically starts working")
                .isCorrect(false)
                .question(question14)
                .build());
        answerRepository.save(Answer.builder().
                answerText("Blame the university Wi-Fi provider")
                .isCorrect(false)
                .question(question14)
                .build());
        answerRepository.save(Answer.builder().
                answerText("Change your major to finance immediately")
                .isCorrect(false)
                .question(question14)
                .build());

        // Question 15
        answerRepository.save(Answer.builder().
                answerText("Accept your fate and prepare your wallet for top-up")
                .isCorrect(true)
                .question(question15)
                .build());
        answerRepository.save(Answer.builder().
                answerText("Borrow your friend's tokens like borrowing money for lunch")
                .isCorrect(false)
                .question(question15)
                .build());
        answerRepository.save(Answer.builder().
                answerText("Stare blankly at the IDE while questioning your life choices")
                .isCorrect(false)
                .question(question15)
                .build());
        answerRepository.save(Answer.builder().
                answerText("Write the rest of the code by hand on a piece of paper")
                .isCorrect(false)
                .question(question15)
                .build());

        /* Declare courses */
        courseRepository.save(Course.builder()
                .video("https://youtu.be/ZZ2QUCePgYw?si=4lXuVGfxJoDP-pAm")
                .name("Introduction to Agentic AI")
                .description("A comprehensive overview of AI agent capabilities, applications, architectures, and use cases to help you understand how intelligent agents are built.")
                .level(level1)
                .build());
        courseRepository.save(Course.builder()
                .video("https://youtu.be/GDm_uH6VxPY?si=74ARxW17-x57awYW")
                .name("AI agent design patterns")
                .description("Learn how to design and build AI agentic systems using the Agent Development Kit (ADK) with code and live demos for foundational architectures.")
                .level(level2)
                .build());
        courseRepository.save(Course.builder()
                .video("https://youtu.be/89KKm_a4M7A?si=v_dNzKfoqGnU25Hx")
                .name("3 Advanced AI agent design patterns")
                .description("Build dynamic multi-agent systems using advanced Agent Development Kit workflows like Loop, Coordinator, and Agent-as-Tool.")
                .level(level3)
                .build());
        courseRepository.save(Course.builder()
                .video("https://youtu.be/pX0_iIfRilU?si=9FE1KLwhjtD0ylTs")
                .name("Foundations of multi-agent systems with ADK")
                .description("Explore decentralized control, local views, agent types, and hierarchies for structuring multi-agent applications with Google's ADK.")
                .level(level4)
                .build());

        /* Declare badges */
        Badge badge1;
        Badge badge2;
        Badge badge3;
        Badge badge4;
        Badge badge5;
        Badge badge6;
        Badge badge7;

        badge1 = Badge.builder()
                .name("Welcome to Boki Passport!")
                .description("You've logged into Boki Passport for the first time!")
                .build();
        badge2 = Badge.builder()
                .name("Hello World!")
                .description("You've learned the first course!")
                .build();
        badge3 = Badge.builder()
                .name("\"Easy peasy lemon squeezy!\"")
                .description("Passed the level 1 exam.")
                .build();
        badge4 = Badge.builder()
                .name("\"Showtime!\"")
                .description("Passed the level 2 exam.")
                .build();
        badge5 = Badge.builder()
                .name("Dance with the BokiAI")
                .description("Passed the level 3 exam.")
                .build();
        badge6 = Badge.builder()
                .name("Stairway to BokiAI expert")
                .description("Passed the level 4 exam.")
                .build();
        badge7 = Badge.builder()
                .name("Better luck next time!")
                .description("Failed the exam for the first time.")
                .build();

        /* Declare users and user accounts with badges inside builder */
        User userAdmin;
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

        userAdmin = userRepository.save(User.builder()
                .firstName("Admin")
                .lastName("Doe")
                .province(null)
                .image("https://thumbs.dreamstime.com/b/sad-man-silhouette-worried-beach-sunset-sun-background-51640055.jpg")
                .levels(List.of(level1, level2, level3, level4))
                .badges(List.of(badge1, badge2, badge3, badge4, badge5, badge6, badge7))
                .build());
        userAccountRepository.save(UserAccount.builder()
                .username("admin")
                .password(encoder.encode("123"))
                .profile(userAdmin)
                .enabled(true)
                .roles(List.of(Role.ROLE_ADMIN, Role.ROLE_USER))
                .build());

        user1 = userRepository.save(User.builder()
                .firstName("Boki")
                .lastName("Chan")
                .province("Chiang Rai")
                .image("https://avatars.githubusercontent.com/u/316038766?s=200&v=4")
                .levels(List.of(level1))
                .badges(List.of(badge1, badge2, badge7))
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
                .badges(List.of(badge1, badge2, badge7, badge3))
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
                .badges(List.of(badge1, badge2, badge3, badge7))
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
                .badges(List.of(badge1, badge2, badge7))
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
                .badges(List.of(badge1, badge2, badge7, badge3))
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
                .badges(List.of(badge1, badge2, badge3, badge4, badge7))
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
                .badges(List.of(badge1, badge2, badge7))
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
                .badges(List.of(badge1, badge2, badge7, badge3))
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
                .badges(List.of(badge1, badge2, badge3, badge7, badge4, badge6, badge5))
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
                .badges(List.of(badge1, badge2, badge7))
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
                .badges(List.of(badge1, badge2, badge3, badge7))
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
                .badges(List.of(badge1, badge2, badge7))
                .build());
        userAccountRepository.save(UserAccount.builder()
                .username("thianlnwza007")
                .password(encoder.encode("123"))
                .profile(user12)
                .enabled(true)
                .roles(List.of(Role.ROLE_USER))
                .build());

        /* Declare user exam histories */
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(1).isPassed(false).exam(exam1).user(user1).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam1).user(user1).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(1).isPassed(false).exam(exam1).user(user1).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam1).user(user1).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam1).user(user1).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam1).user(user1).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(1).isPassed(false).exam(exam1).user(user1).build());

        /* User 2 Exam Histories */
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam1).user(user2).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(1).isPassed(false).exam(exam1).user(user2).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam1).user(user2).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(4).isPassed(true).exam(exam1).user(user2).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam2).user(user2).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(4).isPassed(true).exam(exam2).user(user2).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam3).user(user2).build());

        /* User 3 Exam Histories */
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(5).isPassed(true).exam(exam1).user(user3).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam2).user(user3).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(1).isPassed(false).exam(exam2).user(user3).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam2).user(user3).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam2).user(user3).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam2).user(user3).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(1).isPassed(false).exam(exam2).user(user3).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam2).user(user3).build());

        /* User 4 Exam Histories */
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(1).isPassed(false).exam(exam1).user(user4).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam1).user(user4).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(1).isPassed(false).exam(exam1).user(user4).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam1).user(user4).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam1).user(user4).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(4).isPassed(true).exam(exam1).user(user4).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam2).user(user4).build());

        /* User 5 Exam Histories */
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam1).user(user5).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam1).user(user5).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(3).isPassed(true).exam(exam1).user(user5).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(1).isPassed(false).exam(exam2).user(user5).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam2).user(user5).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam2).user(user5).build());

        /* User 6 Exam Histories */
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(4).isPassed(true).exam(exam1).user(user6).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(4).isPassed(true).exam(exam2).user(user6).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam3).user(user6).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(3).isPassed(false).exam(exam3).user(user6).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam3).user(user6).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(3).isPassed(false).exam(exam3).user(user6).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam3).user(user6).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(4).isPassed(true).exam(exam3).user(user6).build());

        /* User 7 Exam Histories */
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(1).isPassed(false).exam(exam1).user(user7).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam1).user(user7).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(1).isPassed(false).exam(exam1).user(user7).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam1).user(user7).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam1).user(user7).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(1).isPassed(false).exam(exam1).user(user7).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam1).user(user7).build());

        /* User 8 Exam Histories */
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam1).user(user8).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(4).isPassed(true).exam(exam1).user(user8).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam2).user(user8).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(1).isPassed(false).exam(exam2).user(user8).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam2).user(user8).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam2).user(user8).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(1).isPassed(false).exam(exam2).user(user8).build());

        /* User 9 Exam Histories */
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(4).isPassed(true).exam(exam1).user(user9).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam2).user(user9).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam2).user(user9).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(4).isPassed(true).exam(exam2).user(user9).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam3).user(user9).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(3).isPassed(false).exam(exam3).user(user9).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(4).isPassed(true).exam(exam3).user(user9).build());

        /* User 10 Exam Histories */
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(1).isPassed(false).exam(exam1).user(user10).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam1).user(user10).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(1).isPassed(false).exam(exam1).user(user10).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam1).user(user10).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam1).user(user10).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(1).isPassed(false).exam(exam1).user(user10).build());

        /* User 11 Exam Histories */
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(5).isPassed(true).exam(exam1).user(user11).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam2).user(user11).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam2).user(user11).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam2).user(user11).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(1).isPassed(false).exam(exam2).user(user11).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam2).user(user11).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam2).user(user11).build());

        /* User 12 Exam Histories */
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(1).isPassed(false).exam(exam1).user(user12).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam1).user(user12).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(1).isPassed(false).exam(exam1).user(user12).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam1).user(user12).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam1).user(user12).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(2).isPassed(false).exam(exam1).user(user12).build());
        userExamHistoryRepository.save(UserExamHistory.builder().totalScore(1).isPassed(false).exam(exam1).user(user12).build());

        /* Set users for each level */
        level1.setUsers(List.of(userAdmin, user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12));
        level2.setUsers(List.of(userAdmin, user2, user3, user4, user6, user7, user8, user9, user10));
        level3.setUsers(List.of(userAdmin, user2, user6, user7, user8, user9, user10));
        level4.setUsers(List.of(userAdmin, user9));
        /* Add benefits to level */
        levelRepository.saveAll(List.of(level1, level2, level3, level4));
        /* Add badges to users */
        badge1.setUsers(List.of(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12));
        badge2.setUsers(List.of(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12));
        badge3.setUsers(List.of(user2, user3, user5, user6, user8, user9, user11));
        badge4.setUsers(List.of(user6, user9));
        badge5.setUsers(List.of(user9));
        badge6.setUsers(List.of(user9));
        badge7.setUsers(List.of(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12));
        badgeRepository.saveAll(List.of(badge1, badge2, badge3, badge4, badge5, badge6, badge7));

        questionRepository.saveAll(List.of(question1, question2, question3, question4, question5, question6, question7, question8, question9, question10, question11, question12, question13, question14, question15));
    }
}