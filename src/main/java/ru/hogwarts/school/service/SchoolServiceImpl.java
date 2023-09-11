package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.SchoolRepository;

import java.util.Collection;

@Service
public class SchoolServiceImpl implements SchoolService {

    Logger logger = LoggerFactory.getLogger(SchoolServiceImpl.class);
    private final SchoolRepository schoolRepository;
    private final AvatarRepository avatarRepository;

    public SchoolServiceImpl(SchoolRepository schoolRepository, AvatarRepository avatarRepository) {
        this.schoolRepository = schoolRepository;
        this.avatarRepository = avatarRepository;
    }

    @Override
    public Integer getStudentsByQuantity() {
        logger.info("Was invoked method for students by quantity");
        return schoolRepository.getStudentsByQuantity();
    }

    @Override
    public Double getAverageAgeStudents() {
        logger.info("Was invoked method for average age students");
        return schoolRepository.getAverageAgeStudents();
    }

    @Override
    public Collection<Student> getLastFiveStudents() {
        logger.info("Was invoked method for last five students");
        return schoolRepository.getLastFiveStudents();
    }
}
