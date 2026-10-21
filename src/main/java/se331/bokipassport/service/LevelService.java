package se331.bokipassport.service;

import se331.bokipassport.entity.Level;

import java.util.List;

public interface LevelService {
    List<Level> findAll();
    Level findById(Long id);
}
