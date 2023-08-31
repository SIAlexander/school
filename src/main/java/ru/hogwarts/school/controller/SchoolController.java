package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.SchoolService;

import java.util.Collection;

@RestController
@RequestMapping
public class SchoolController {

    private final SchoolService schoolService;
    private final AvatarService avatarService;

    public SchoolController(SchoolService schoolService, AvatarService avatarService) {
        this.schoolService = schoolService;
        this.avatarService = avatarService;
    }

    @GetMapping("/getStudentsByQuantity")
    public Integer getStudentsByQuantity(){
        return schoolService.getStudentsByQuantity();
    }

    @GetMapping("/getAverageAgeStudents")
    public Double getAverageAgeStudents(){
        return schoolService.getAverageAgeStudents();
    }

    @GetMapping("/getLastFiveStudents")
    public ResponseEntity<Collection<Student>> getLastFiveStudents(){
        return ResponseEntity.ok(schoolService.getLastFiveStudents());
    }
}
