package se331.bokipassport.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserExamHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    Long id;
    @CreationTimestamp
    Instant createdAt;
    Integer totalScore;
    Boolean isPassed;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User userList;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    Exam examList;
}
