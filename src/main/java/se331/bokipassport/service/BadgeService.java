package se331.bokipassport.service;

import se331.bokipassport.entity.Badge;

import java.util.List;

public interface BadgeService {
    List<Badge> findAll();
    Badge findById(Long id);
}
