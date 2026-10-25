package se331.bokipassport.dao;

import se331.bokipassport.entity.Level;

import java.util.List;

public interface LevelDao {
    List<Level> findAll();
    Level findById(Long id);
    Level findNextAfter(Long levelId);
}
