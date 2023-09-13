package ru.hogwarts.school.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<Student, Integer> {

    @Query(value = "SELECT COUNT(id) FROM student", nativeQuery = true)
    Integer getStudentsByQuantity();

    @Query(value = "SELECT AVG(age) from student", nativeQuery = true)
    Double getAverageAgeStudents();

    @Query(value= "select * from student order by id desc limit 5", nativeQuery = true)
    List<Student> getLastFiveStudents();
}
