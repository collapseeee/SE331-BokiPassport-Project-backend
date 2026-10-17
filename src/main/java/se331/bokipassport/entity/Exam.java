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

    @OneToOne
    Level level;

    @OneToMany(mappedBy = "examList")
    List<UserExamHistory> examHistories;
}
