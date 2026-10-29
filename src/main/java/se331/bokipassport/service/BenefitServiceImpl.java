package se331.bokipassport.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se331.bokipassport.dao.BenefitDao;
import se331.bokipassport.dao.LevelDao;
import se331.bokipassport.entity.Benefit;
import se331.bokipassport.entity.Level;
import se331.bokipassport.entity.dto.BenefitDTO;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BenefitServiceImpl implements BenefitService {
    final BenefitDao benefitDao;
    final LevelDao levelDao;

    @Override
    public List<Benefit> findAll() {
        return benefitDao.findAll();
    }

    @Override
    public List<Benefit> findByLevelId(Long levelId) {
        return benefitDao.findByLevelId(levelId);
    }

    @Override
    public Benefit create(Long levelId, BenefitDTO benefitDTO) {
        Level level = levelDao.findById(levelId);
        if (level == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Level not found with id: " + levelId + " not found");
        }

        Benefit benefit = Benefit.builder()
                .description(benefitDTO.getDescription())
                .level(level)
                .build();
        return benefitDao.save(benefit);
    }

    @Override
    public Benefit save(Long benefitId, BenefitDTO benefitDTO) {
        Benefit benefit = benefitDao.findById(benefitId);
        if (benefit == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Benefit with id " + benefitId + " not found");
        }
        Level level = levelDao.findById(benefit.getLevel().getId());
        if (level == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Level with id" + benefitDTO.getLevel().getId() + " not found");
        }
        benefit.setId(benefit.getId());
        if (!isEmpty(benefitDTO.getDescription())) {
            // If incoming benefit description is not "" or null, use the incoming one.
            benefit.setDescription(benefitDTO.getDescription());
        }
        if (benefitDTO.getLevel() != null && benefit.getLevel().getId().equals(level.getId())) {
            // If incoming benefit's level is not null and it differs from the existed one. use the incoming one.
            Level newLevel = levelDao.findById(benefitDTO.getLevel().getId());
            benefit.setLevel(newLevel);
        }
        return benefitDao.save(benefit);
    }

    @Override
    @Transactional
    public List<Benefit> saveAll(Long levelId, List<BenefitDTO> inputBenefitDTOs) {
        List<Benefit> savedBenefits = new ArrayList<>();
        for (BenefitDTO benefitDTO : inputBenefitDTOs) {
            if (benefitDTO.getId() != null) {
                savedBenefits.add(save(benefitDTO.getId(), benefitDTO));
            } else {
                savedBenefits.add(create(levelId, benefitDTO));
            }
        }
        return savedBenefits;
    }

    @Override
    public void delete(Long benefitId) {
        Benefit benefit = benefitDao.findById(benefitId);
        if (benefit == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Benefit with id " + benefitId + " not found");
        }
        benefitDao.delete(benefit);
    }

    @Override
    public void deleteAll(List<Long> ids) {
        for (Long id : ids) {
            this.delete(id);
        }
    }

    private boolean isEmpty(String string) {
        return  string == null || string.isEmpty();
    }
}
