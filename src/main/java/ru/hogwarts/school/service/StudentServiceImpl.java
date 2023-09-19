package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;
    private int count = 0;

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
    public Collection<Student> getStudentsFaculty(Long studentId) {
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

    @Override
    public Collection<String> getStudentBySortingFirstLetterName() {
        logger.info("Was invoked method for getStudentBySortingFirstLetterName");
        return getAllStudents().stream()
                .sorted(Comparator.comparing(Student::getName))
                .map(s -> s.getName().toUpperCase())
                .filter(n -> n.startsWith("A"))
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageAgeStudents() {
        logger.info("Was invoked method for getAverageAgeStudents");
        return getAllStudents().stream()
                .collect(Collectors.averagingInt(Student::getAge));
    }

    @Override
    public void getAllStudentsThread() {
        logger.info("Was invoked method for getAllStudentsThread");
        List<String> listStudentName = getAllStudents().stream().map(Student::getName).toList();

        logger.info(listStudentName.get(0));
        logger.info(listStudentName.get(1));

        new Thread(() -> {
            logger.info(listStudentName.get(2));
            logger.info(listStudentName.get(3));
        }).start();

        new Thread(() -> {
            logger.info(listStudentName.get(4));
            logger.info(listStudentName.get(5));
        }).start();
    }

    @Override
    public void getAllStudentsThreadSynchronized() {
        logger.info("Was invoked method for getAllStudentsThreadSynchronized");
        List<String> listStudentName = getAllStudents().stream().map(Student::getName).toList();

        printNameStudent(listStudentName);
        printNameStudent(listStudentName);

        new Thread(() -> {
            printNameStudent(listStudentName);
            printNameStudent(listStudentName);
        }).start();

        new Thread(() -> {
            printNameStudent(listStudentName);
            printNameStudent(listStudentName);
        }).start();
    }

    public synchronized void printNameStudent(List<String> listStudentName) {
        logger.info(listStudentName.get(count++ % listStudentName.size()));
    }
}
