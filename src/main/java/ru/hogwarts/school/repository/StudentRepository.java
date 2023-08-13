package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findStudentsByAge(int studentAge);

    List<Student> findByAgeBetween(int minAge, int maxAge);

    List<Student> findByFaculty_Id(Long studentId);

}
