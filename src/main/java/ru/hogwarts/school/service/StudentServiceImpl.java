package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentServiceImpl implements StudentService {
    private Map<Long, Student> students = new HashMap<>();
    private Long generateStudentId = 0L;

    @Override
    public Student createStudent(Student student) {
        student.setId(++generateStudentId);
        students.put(generateStudentId, student);
        return student;
    }

    @Override
    public Student getStudent(Long studentId) {
        return students.get(studentId);
    }

    @Override
    public Student updateStudent(Long studentId, Student student) {
        students.put(generateStudentId, student);
        return student;
    }

    @Override
    public Student removeStudent(Long studentId) {
        return students.remove(studentId);
    }

    @Override
    public Collection<Student> getStudentsByAge(int age) {
        return students.values().stream()
                .filter(e -> e.getAge() == age)
                .collect(Collectors.toList());
    }
}
