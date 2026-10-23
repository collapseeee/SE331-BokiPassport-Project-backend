package se331.bokipassport.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    Long id;
    String name;
    String description;

    @ManyToOne
    Level level;

    @OneToMany(mappedBy = "exam")
    List<UserExamHistory> examHistories;

    @OneToMany(mappedBy = "exam")
    List<Question> questions;
}
