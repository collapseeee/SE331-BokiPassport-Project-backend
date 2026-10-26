package se331.bokipassport.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se331.bokipassport.dao.LevelDao;
import se331.bokipassport.entity.Level;
import se331.bokipassport.entity.dto.LevelDTO;
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

    @Override
    public Level save(Long levelId, LevelDTO levelDTO) {
        Level level = this.findById(levelId);
        if (level == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Level with id " + levelId + " not found");
        }

        if (Boolean.FALSE.equals(isEmpty(levelDTO.getName()))) {
            level.setName(levelDTO.getName());
        }
        if (Boolean.FALSE.equals(isEmpty(levelDTO.getDescription()))) {
            level.setDescription(levelDTO.getDescription());
        }
        if (Boolean.FALSE.equals(levelDTO.getPassCriteria())) {
            level.setPassCriteria(levelDTO.getPassCriteria());
        }
        return levelDao.save(level);
    }

    private Boolean isEmpty(String text) {
        return text == null || text.isEmpty();
    }
}
