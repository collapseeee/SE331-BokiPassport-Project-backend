package se331.bokipassport.entity;

import jakarta.persistence.*;
import lombok.*;
import se331.bokipassport.security.user.UserAccount;

import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    Long id;
    String firstName;
    String lastName;
    String province;
    String image;

    @OneToMany(mappedBy = "user")
    List<UserExamHistory> userExamHistories;

    @ManyToMany
    List<Badge> badges;

    @ManyToMany
    List<Level> levels;

    @OneToOne
    UserAccount account;
}
