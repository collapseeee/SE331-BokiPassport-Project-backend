package se331.bokipassport.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se331.bokipassport.dao.BenefitDao;
import se331.bokipassport.dao.LevelDao;
import se331.bokipassport.entity.Benefit;
import se331.bokipassport.entity.Level;
import se331.bokipassport.entity.dto.BenefitDTO;

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
    public Benefit save(BenefitDTO benefitDTO) {
        Level level = levelDao.findById(benefitDTO.getLevel().getId());
        if (level == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Level with id" + benefitDTO.getLevel().getId() + " not found");
        }

        Benefit existedBenefit = benefitDao.findById(benefitDTO.getId());
        Benefit newBenefit = new Benefit(null, benefitDTO.getDescription(), level);
        if (existedBenefit != null) {
            newBenefit.setId(existedBenefit.getId());
            if (isEmpty(benefitDTO.getDescription())) { // If incoming benefit description is "" or null, use the existed one.
                newBenefit.setDescription(existedBenefit.getDescription());
            } else {
                newBenefit.setDescription(benefitDTO.getDescription());
            }
        }
        return benefitDao.save(newBenefit);
    }

    private boolean isEmpty(String string) {
        return  string == null || string.isEmpty();
    }
}
