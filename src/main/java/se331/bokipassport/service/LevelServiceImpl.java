package se331.bokipassport.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se331.bokipassport.dao.LevelDao;
import se331.bokipassport.entity.Level;
import se331.bokipassport.repository.LevelRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class LevelServiceImpl implements LevelService {
    final LevelDao levelDao;

    @Override
    public List<Level> findAll() {
        return levelDao.findAll();
    }

    @Override
    public Level findById(Long id) {
        return levelDao.findById(id);
    }
}
