package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StudentServiceImplTest {
    private Student studentOne;
    private Student studentTwo;
    StudentServiceImpl studentService = new StudentServiceImpl();

    @BeforeEach
    void setUp() {
        studentOne = new Student(1L,"Петр", 17);
        studentTwo = new Student(2L,"Василий", 17);
    }

    @Test
    void createStudent() {
        Student actual = studentService.createStudent(studentOne);
        Student expected = new Student(1L, "Петр", 17);
        assertEquals(expected, actual);
    }

    @Test
    void getStudent(){
        studentService.createStudent(studentOne);
        assertNotNull(studentService.getStudent(1L));
    }

    @Test
    void updateStudent(){
        Student student = studentService.createStudent(studentOne);
        assertNotNull(studentService.updateStudent(1L, student));
    }

    @Test
    void removeStudent(){
        studentService.createStudent(studentOne);
        assertNotNull(studentService.removeStudent(1L));
    }

    @Test
    void getStudentsByAge(){
        studentService.createStudent(studentOne);
        studentService.createStudent(studentTwo);
        List< Student> expectedList = List.of(studentOne,studentTwo);

        assertEquals(expectedList, studentService.getStudentsByAge(17));
    }
}
