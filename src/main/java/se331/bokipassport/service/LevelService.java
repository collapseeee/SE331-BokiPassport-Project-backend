package se331.bokipassport.service;

import se331.bokipassport.entity.Level;
import se331.bokipassport.entity.dto.LevelDTO;

import java.util.List;

public interface LevelService {
    List<Level> findAll();
    Level findById(Long id);
    Level save(Long levelId, LevelDTO levelDTO);
}
