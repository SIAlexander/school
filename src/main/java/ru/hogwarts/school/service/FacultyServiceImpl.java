package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.service.repository.FacultyRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFacultyById(Long facultyId) {
        logger.info("Was invoked method for find faculty");
        boolean exists = facultyRepository.existsById(facultyId);
        if (!exists) {
            logger.error("There is not faculty with id = {}", facultyId);
            throw new FacultyNotFoundException();
        }
        return facultyRepository.findById(facultyId).get();
    }

    @Override
    public Collection<Faculty> getAllFaculty() {
        logger.info("Was invoked method for find all faculties");
        return facultyRepository.findAll();
    }

    @Override
    public Faculty getStudentFaculty(Long studentId) {
        logger.info("Was invoked method for find faculty students");
        return facultyRepository.findByStudent_Id(studentId).orElseThrow(FacultyNotFoundException::new);
    }

    @Override
    public Faculty updateFaculty(Long facultyId, Faculty faculty) {
        logger.info("Was invoked method for update faculty");
        boolean exists = facultyRepository.existsById(facultyId);
        if (!exists) {
            logger.error("There is not faculty with id = {}", facultyId);
            throw new FacultyNotFoundException();
        }
        return facultyRepository.save(faculty);
    }

    @Override
    public void removeFaculty(Long facultyId) {
        logger.info("Was invoked method for remove faculty");
        boolean exists = facultyRepository.existsById(facultyId);
        if (!exists) {
            logger.error("There is not faculty with id = {}", facultyId);
            throw new FacultyNotFoundException();
        }
        facultyRepository.deleteById(facultyId);
    }

    @Override
    public Collection<Faculty> getFacultyByColor(String color) {
        logger.info("Was invoked method for faculty by color");
        return facultyRepository.findFacultyByColorIgnoreCase(color);
    }

    @Override
    public Collection<Faculty> getFacultyByName(String name) {
        logger.info("Was invoked method for faculty by name");
        return facultyRepository.findFacultyByNameIgnoreCase(name);
    }

    @Override
    public String getFacultyByLongestName(){
        logger.info("Was invoked method for Faculty By Longest Name");
        return getAllFaculty().stream()
                .max(Comparator.comparing(n -> n.getName().length()))
                .get().getName();
    }
}
