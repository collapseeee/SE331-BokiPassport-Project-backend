package se331.bokipassport.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import se331.bokipassport.dao.ExamDao;
import se331.bokipassport.dao.LevelDao;
import se331.bokipassport.entity.Exam;
import se331.bokipassport.entity.Level;
import se331.bokipassport.entity.dto.ExamDTO;

import java.util.List;

@Service
@AllArgsConstructor
public class ExamServiceImpl implements ExamService {
    final ExamDao examDao;
    final LevelDao levelDao;

    @Override
    public List<Exam> findAll() {
        return examDao.findAll();
    }

    @Override
    public Exam findById(Long id) {
        return examDao.findById(id);
    }

    @Override
    public Exam save(Long id, ExamDTO examDTO) {
        Exam exam = examDao.findById(id);
        if (exam == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Exam with id " + id + " not found"
            );
        }

        if (Boolean.FALSE.equals(isEmpty(examDTO.getName()))) {
            exam.setName(examDTO.getName());
        }
        if (Boolean.FALSE.equals(isEmpty(examDTO.getDescription()))) {
            exam.setDescription(examDTO.getDescription());
        }
        if (examDTO.getLevel() != null && examDTO.getLevel().getId() != null) {
            Level level = levelDao.findById(examDTO.getLevel().getId());
            if (level != null) {
                exam.setLevel(level);
            }
        }
        return examDao.save(exam);
    }

    public Boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
