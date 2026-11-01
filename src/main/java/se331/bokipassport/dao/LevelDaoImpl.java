package se331.bokipassport.dao;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import se331.bokipassport.entity.Level;
import se331.bokipassport.repository.LevelRepository;

import java.util.List;

@Repository
@AllArgsConstructor
public class LevelDaoImpl implements LevelDao {
    final LevelRepository levelRepository;

    @Override
    public List<Level> findAll() {
        return levelRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Level findById(Long id) {
        return levelRepository.findById(id).orElse(null);
    }

    @Override
    public Level findNextAfter(Long levelId) {
        return levelRepository.findFirstByIdGreaterThanOrderByIdAsc(levelId).orElse(null);
    }

    @Override
    public Level save(Level level) {
        return levelRepository.save(level);
    }
}
