package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student createStudent(Student student);

    Student getStudentById(Long studentId);

    Collection<Student> getAllStudents();

    Collection<Student> getByAgeBetween(int minAge, int maxAge);

    Collection<Student> getStudentsFaculty(Long studentId);

    Student updateStudent(Long studentId, Student student);

    void removeStudent(Long studentId);

    Collection<Student> getStudentsByAge(int studentAge);
}
