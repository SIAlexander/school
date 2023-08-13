package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    Faculty getFacultyById(Long facultyId);

    Collection<Faculty> getAllFaculty();

    Faculty getStudentFaculty(Long studentId);

    Faculty updateFaculty(Long facultyId, Faculty faculty);

    void removeFaculty(Long facultyId);

    Collection<Faculty> getFacultyByColor(String color);

    Collection<Faculty> getFacultyByName(String name);

}
