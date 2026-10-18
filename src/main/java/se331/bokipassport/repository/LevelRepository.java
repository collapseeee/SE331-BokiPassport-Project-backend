package se331.bokipassport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.bokipassport.entity.Level;

public interface LevelRepository extends JpaRepository<Level, Long> {
}
