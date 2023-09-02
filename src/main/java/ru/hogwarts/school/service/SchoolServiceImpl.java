package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.SchoolRepository;

import java.util.Collection;

@Service
public class SchoolServiceImpl implements SchoolService{

    private final SchoolRepository schoolRepository;
    private final AvatarRepository avatarRepository;

    public SchoolServiceImpl(SchoolRepository schoolRepository, AvatarRepository avatarRepository) {
        this.schoolRepository = schoolRepository;
        this.avatarRepository = avatarRepository;
    }

    @Override
    public Integer getStudentsByQuantity(){
        return schoolRepository.getStudentsByQuantity();
    }

    @Override
    public Double getAverageAgeStudents(){
        return schoolRepository.getAverageAgeStudents();
    }

    @Override
    public Collection<Student> getLastFiveStudents(){
        return schoolRepository.getLastFiveStudents();
    }
}
