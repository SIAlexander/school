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
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private StudentServiceImpl studentServiceImpl;

    @Test
    public void createStudentTest() throws Exception {
        final long id = 1;
        final String name = "Sergey";
        final int age = 27;

        Faculty faculty = new Faculty(1L, "ur-fak", "red");
        Student student = new Student(1L, "Sergey", 27, faculty);

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("age", age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void getStudentsTest() throws Exception {
        Faculty faculty = new Faculty(1L, "ur-fak", "red");
        Student student = new Student(1L, "Sergey", 27, faculty);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateStudentTest() throws Exception {
        Faculty faculty = new Faculty(1L, "ur-fak", "red");
        Student student = new Student(1L, "Sergey", 27, faculty);

        when(studentRepository.existsById(1L)).thenReturn(true);
        when(studentRepository.save(ArgumentMatchers.any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(objectMapper.writeValueAsString(student))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sergey"))
                .andExpect(jsonPath("$.age").value(27));
    }

    @Test
    public void removeStudentTest() throws Exception {
        Faculty faculty = new Faculty(1L, "ur-fak", "red");
        Student student = new Student(1L, "Sergey", 27, faculty);

        when(studentRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/student/{studentId}", student.getId())
                        .content(objectMapper.writeValueAsString(student))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudentsFacultyTest() throws Exception {
        final String name = "Sergey";
        final int age = 27;

        Faculty faculty = new Faculty(1L, "ur-fak", "red");

        when(studentRepository.findByFaculty_Id(1L)).thenReturn(Arrays.asList(
                new Student(1L, "Sergey", 27, faculty),
                new Student(2L, "Petr", 23, faculty)));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/showStudentsFaculty?studentId=1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect((jsonPath("$[0].age").value(age)));
    }

    @Test
    public void getStudentsByAgeTest() throws Exception{
        final String name = "Sergey";
        final int age = 27;

        Faculty faculty = new Faculty(1L, "ur-fak", "red");

        when(studentRepository.findStudentsByAge(27)).thenReturn(Arrays.asList(
                new Student(1L, "Sergey", 27, faculty),
                new Student(2L, "Petr", 23, faculty)));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/findStudentsAge?studentAge=27")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect((jsonPath("$[0].age").value(age)));

        when(studentRepository.findByAgeBetween(20, 30)).thenReturn(Arrays.asList(
                new Student(1L, "Sergey", 27, faculty),
                new Student(2L, "Petr", 23, faculty)));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/findStudentsAge?minAge=20&maxAge=30")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect((jsonPath("$[0].age").value(age)));
    }
}
