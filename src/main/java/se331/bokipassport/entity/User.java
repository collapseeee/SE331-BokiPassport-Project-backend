package se331.bokipassport.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @OneToMany(mappedBy = "userList")
    List<UserExamHistory> userExamHistories;

    @OneToMany(mappedBy = "user")
    List<Badge> badges;

    @OneToMany(mappedBy = "user")
    List<Level> levels;
}
