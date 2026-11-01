package se331.bokipassport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.bokipassport.entity.Level;

import java.util.Optional;

public interface LevelRepository extends JpaRepository<Level, Long> {
    Level findByName(String name);
    Optional<Level> findFirstByIdGreaterThanOrderByIdAsc(Long id);
}
