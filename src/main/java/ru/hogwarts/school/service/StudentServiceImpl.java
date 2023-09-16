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
    public final Object flag = new Object();

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
        List<String> listStudentName = getAllStudents().stream().map(Student::getName).toList();

        System.out.println(listStudentName.get(0));

        new Thread(() -> {
            System.out.println(listStudentName.get(2));
            System.out.println(listStudentName.get(3));
        }).start();

        new Thread(() -> {
            System.out.println(listStudentName.get(4));
            System.out.println(listStudentName.get(5));
        }).start();

        System.out.println(listStudentName.get(1));
    }

    @Override
    public void getAllStudentsThreadSynchronized() {
        List<String> listStudentName = getAllStudents().stream().map(Student::getName).toList();

        printNameStudent(listStudentName.get(0));

        new Thread(() -> {
            printNameStudent(listStudentName.get(2));
            printNameStudent(listStudentName.get(3));
        }).start();

        new Thread(() -> {
            printNameStudent(listStudentName.get(4));
            printNameStudent(listStudentName.get(5));
        }).start();

        printNameStudent(listStudentName.get(1));

    }

    public void printNameStudent(String name) {
        synchronized (flag) {
            System.out.println(name);
        }
    }
}
