package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.repository.FacultyRepository;
import ru.hogwarts.school.service.repository.StudentRepository;
import ru.hogwarts.school.service.*;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private FacultyServiceImpl facultyServiceImpl;

    @Test
    public void createFacultyTest() throws Exception {
        final long id = 1;
        final String name = "ur-fak";
        final String color = "red";

        Faculty faculty = new Faculty(1L, "ur-fak", "red");

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void getFacultyTest() throws Exception {
        Faculty faculty = new Faculty(1L, "ur-fak", "red");

        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudentFacultyTest() throws Exception {
        final String name = "ur-fak";
        final String color = "red";

        Faculty faculty = new Faculty(1L, "ur-fak", "red");
        Student student = new Student(1L, "Petr", 23, faculty);
        student.setFaculty(faculty);

        when(facultyRepository.findByStudent_Id(1L)).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/showStudentFaculty?studentId=1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect((jsonPath("$.color").value(color)));
    }

    @Test
    public void updateFaculty() throws Exception {
        Faculty faculty = new Faculty(1L, "ur-fak", "red");

        when(facultyRepository.existsById(1L)).thenReturn(true);
        when(facultyRepository.save(ArgumentMatchers.any(Faculty.class))).thenReturn(faculty);


        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(objectMapper.writeValueAsString(faculty))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ur-fak"))
                .andExpect(jsonPath("$.color").value("red"));
    }

    @Test
    public void removeFacultyTest() throws Exception {
        Faculty faculty = new Faculty(2l, "ur-fak", "red");

        when(facultyRepository.existsById(2L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/{facultyId}", faculty.getId())
                        .content(objectMapper.writeValueAsString(faculty))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void getFacultyByColorNameTest() throws Exception {
        final String name = "ur-fak";
        final String color = "red";

        when(facultyRepository.findFacultyByColorIgnoreCase("red")).thenReturn(Arrays.asList(
                new Faculty(1L, "ur-fak", "red"),
                new Faculty(2L, "mat-fak", "blue")));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/findFaculty?color=red")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect((jsonPath("$[0].color").value(color)));

        when(facultyRepository.findFacultyByNameIgnoreCase("mat-fak")).thenReturn(Arrays.asList(
                new Faculty(1L, "ur-fak", "red"),
                new Faculty(2L, "mat-fak", "blue")));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/findFaculty?name=mat-fak")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect((jsonPath("$[0].color").value(color)));
    }
}
