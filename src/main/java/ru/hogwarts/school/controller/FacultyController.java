package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty facultyAdd = facultyService.createFaculty(faculty);
        return ResponseEntity.ok(facultyAdd);
    }

    @GetMapping()
    public ResponseEntity getFaculty(@RequestParam(required = false) Long facultyId) {
        if (facultyId != null) {
            Faculty faculty = facultyService.getFacultyById(facultyId);
            return ResponseEntity.ok(faculty);
        }
        return ResponseEntity.ok(facultyService.getAllFaculty());
    }

    @GetMapping("/showStudentFaculty")
    public ResponseEntity<Faculty> getStudentFaculty(@RequestParam Long studentId) {
        return ResponseEntity.ok(facultyService.getStudentFaculty(studentId));
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty updatedFaculty = facultyService.updateFaculty(faculty.getId(), faculty);
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping("{facultyId}")
    public ResponseEntity removeFaculty(@PathVariable Long facultyId) {
        facultyService.removeFaculty(facultyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findFaculty")
    public ResponseEntity<Collection<Faculty>> getFacultyByColor(@RequestParam(required = false) String color,
                                                                 @RequestParam(required = false) String name) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.getFacultyByColor(color));
        }
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(facultyService.getFacultyByName(name));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
}
