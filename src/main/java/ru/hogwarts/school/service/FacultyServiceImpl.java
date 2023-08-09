package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFaculty(Long facultyId) {
        boolean exists = facultyRepository.existsById(facultyId);
        if (!exists) {
            throw new FacultyNotFoundException();
        }
        return facultyRepository.findById(facultyId).get();
    }

    @Override
    public Faculty updateFaculty(Long facultyId, Faculty faculty) {
        boolean exists = facultyRepository.existsById(facultyId);
        if (!exists) {
            throw new FacultyNotFoundException();
        }
        return facultyRepository.save(faculty);
    }

    @Override
    public void removeFaculty(Long facultyId) {
        boolean exists = facultyRepository.existsById(facultyId);
        if (!exists) {
            throw new FacultyNotFoundException();
        }
        facultyRepository.deleteById(facultyId);
    }

    @Override
    public Collection<Faculty> getFacultyByColor(String color) {
        return facultyRepository.findFacultyByColor(color);
    }
}
