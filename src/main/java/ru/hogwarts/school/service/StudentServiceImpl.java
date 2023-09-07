package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService {

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long studentId) {
        logger.info("Was invoked method for find student");
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            logger.error("There is not student with id = {}", studentId);
            throw new StudentNotFoundException();
        }
        return studentRepository.findById(studentId).get();
    }

    @Override
    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for find all students");
        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> getByAgeBetween(int minAge, int maxAge) {
        logger.debug("findByAgeBetween: min age {} max age {}", minAge, maxAge);
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public Collection<Student> getStudentsFaculty(Long studentId){
        logger.info("Was invoked method for find students faculty");
        return studentRepository.findByFaculty_Id(studentId);
    }

    @Override
    public Student updateStudent(Long studentId, Student student) {
        logger.info("Was invoked method for update student");
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            logger.error("There is not student with id = {}", studentId);
            throw new StudentNotFoundException();
        }
        return studentRepository.save(student);
    }

    @Override
    public void removeStudent(Long studentId) {
        logger.info("Was invoked method for remove student");
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            logger.error("There is not student with id = {}", studentId);
            throw new StudentNotFoundException();
        }
        studentRepository.deleteById(studentId);
    }

    @Override
    public Collection<Student> getStudentsByAge(int studentAge) {
        logger.info("Was invoked method for find student by age");
        return studentRepository.findStudentsByAge(studentAge);
    }
}
