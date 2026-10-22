package se331.bokipassport.dao;

import se331.bokipassport.entity.Badge;

import java.util.List;

public interface BadgeDao {
    List<Badge> findAll();
    Badge findById(Long id);
}
