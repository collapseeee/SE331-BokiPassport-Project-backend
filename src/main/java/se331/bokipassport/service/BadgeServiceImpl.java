package se331.bokipassport.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se331.bokipassport.dao.BadgeDao;
import se331.bokipassport.entity.Badge;

import java.util.List;

@Service
@AllArgsConstructor
public class BadgeServiceImpl implements BadgeService {
    final BadgeDao badgeDao;

    @Override
    public List<Badge> findAll() {
        return badgeDao.findAll();
    }

    @Override
    public Badge findById(Long id) {
        return badgeDao.findById(id);
    }

    @Override
    public List<Badge> findByUserId(Long id) {
        return badgeDao.findByUserId(id);
    }
}
