package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface SchoolService {

    Integer getStudentsByQuantity();

    Double getAverageAgeStudents();

    Collection<Student> getLastFiveStudents();

}
