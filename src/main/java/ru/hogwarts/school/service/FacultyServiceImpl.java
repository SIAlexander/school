package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private Map<Long, Faculty> faculties = new HashMap<>();
    private Long generateFacultyId = 0L;

    @Override
    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++generateFacultyId);
        faculties.put(generateFacultyId, faculty);
        return faculty;
    }

    @Override
    public Faculty getFaculty(Long facultyId) {
        return faculties.get(facultyId);
    }

    @Override
    public Faculty updateFaculty(Long facultyId, Faculty faculty) {
        faculties.put(generateFacultyId, faculty);
        return faculty;
    }

    @Override
    public Faculty removeFaculty(Long facultyId) {
        return faculties.remove(facultyId);
    }

    @Override
    public Collection<Faculty> getFacultyByColor(String color) {
        return faculties.values().stream()
                .filter(e->e.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
