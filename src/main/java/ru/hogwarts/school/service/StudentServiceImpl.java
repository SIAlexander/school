package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new StudentNotFoundException();
        }
        return studentRepository.findById(studentId).get();
    }

    @Override
    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> getByAgeBetween(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public Collection<Student> getStudentsFaculty(Long studentId){
        return studentRepository.findByFaculty_Id(studentId);
    }

    @Override
    public Student updateStudent(Long studentId, Student student) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new StudentNotFoundException();
        }
        return studentRepository.save(student);
    }

    @Override
    public void removeStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new StudentNotFoundException();
        }
        studentRepository.deleteById(studentId);
    }

    @Override
    public Collection<Student> getStudentsByAge(int studentAge) {
        return studentRepository.findStudentsByAge(studentAge);
    }
}
