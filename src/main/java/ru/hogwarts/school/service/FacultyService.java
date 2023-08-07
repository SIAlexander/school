package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    Faculty getFaculty(Long facultyId);

    Faculty updateFaculty(Long facultyId, Faculty faculty);

    Faculty removeFaculty(Long facultyId);

    Collection<Faculty> getFacultyByColor(String color);

}
