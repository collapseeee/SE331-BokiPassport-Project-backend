package se331.bokipassport.dao;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import se331.bokipassport.entity.Badge;
import se331.bokipassport.repository.BadgeRepository;

import java.util.List;

@Repository
@AllArgsConstructor
public class BadgeDaoImpl implements BadgeDao {
    final BadgeRepository badgeRepository;

    @Override
    public List<Badge> findAll() {
        return badgeRepository.findAll();
    }

    @Override
    public Badge findById(Long id) {
        return badgeRepository.findById(id).orElse(null);
    }
}