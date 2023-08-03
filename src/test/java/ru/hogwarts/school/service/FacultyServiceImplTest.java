package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FacultyServiceImplTest {
    private Faculty facultyOne;
    private Faculty facultyTwo;
    FacultyServiceImpl facultyService = new FacultyServiceImpl();

    @BeforeEach
    void setUp() {
        facultyOne = new Faculty(1L, "Юридический", "red");
        facultyTwo = new Faculty(2L, "Исторический", "red");
    }

    @Test
    void createFaculty() {
        Faculty actual = facultyService.createFaculty(facultyOne);
        Faculty expected = new Faculty(1L, "Юридический", "red");
        assertEquals(expected, actual);
    }

    @Test
    void getFaculty() {
        facultyService.createFaculty(facultyOne);
        assertNotNull(facultyService.getFaculty(1L));
    }

    @Test
    void updateFaculty() {
        Faculty faculty = facultyService.createFaculty(facultyOne);
        assertNotNull(facultyService.updateFaculty(1L, faculty));
    }

    @Test
    void removeFaculty() {
        facultyService.createFaculty(facultyOne);
        assertNotNull(facultyService.removeFaculty(1L));
    }

    @Test
    void getFacultyByColor() {
        facultyService.createFaculty(facultyOne);
        facultyService.createFaculty(facultyTwo);
        List<Faculty> expectedList = List.of(facultyOne, facultyTwo);

        assertEquals(expectedList, facultyService.getFacultyByColor("red"));
    }
}
