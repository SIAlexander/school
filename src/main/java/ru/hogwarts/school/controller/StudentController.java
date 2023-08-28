package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student studentAdd = studentService.createStudent(student);
        return ResponseEntity.ok(studentAdd);
    }

    @GetMapping()
    public ResponseEntity getStudents(@RequestParam(required = false) Long studentId) {
        if (studentId != null) {
            Student student = studentService.getStudentById(studentId);
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(student.getId(), student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity removeStudent(@PathVariable Long studentId) {
        studentService.removeStudent(studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/showStudentsFaculty")
    public ResponseEntity<Collection<Student>> getStudentsFaculty(@RequestParam Long studentId) {
        return ResponseEntity.ok(studentService.getStudentsFaculty(studentId));
    }

    @GetMapping(("/findStudentsAge"))
    public ResponseEntity<Collection<Student>> getStudentsByAge(@RequestParam(required = false) Integer studentAge,
                                                                @RequestParam(required = false) Integer minAge,
                                                                @RequestParam(required = false) Integer maxAge) {
        if (studentAge != null && studentAge > 0) {
            return ResponseEntity.ok(studentService.getStudentsByAge(studentAge));
        }
        if (minAge != null && maxAge != null) {
            return ResponseEntity.ok(studentService.getByAgeBetween(minAge, maxAge));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
}
