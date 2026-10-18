package se331.bokipassport.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    Long id;
    String name;
    String description;
    Double passCriteria;

    @OneToMany(mappedBy = "level")
    List<Course> courses;

    @ManyToMany
    List<User> users;
}
