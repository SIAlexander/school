package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student createStudent(Student student);

    Student getStudent(Long studentId);

    Student updateStudent(Long studentId, Student student);

    Student removeStudent(Long studentId);

    Collection<Student> getStudentsByAge(int age);
}
