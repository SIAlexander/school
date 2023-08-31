package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.SchoolRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarServiceImpl;
import ru.hogwarts.school.service.SchoolServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SchoolController.class)
public class SchoolControllerTestWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SchoolRepository schoolRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private FacultyRepository facultyRepositoryImpl;

    @SpyBean
    private SchoolServiceImpl schoolService;

    @SpyBean
    private AvatarServiceImpl avatarService;

    @Test
    public void getStudentsByQuantity() throws Exception {
        int expValue = 2;
        Faculty faculty = new Faculty(1L, "ur-fak", "red");
        List<Student> students = Arrays.asList(
                new Student(1L, "Sergey", 27, faculty),
                new Student(2L, "Petr", 23, faculty));

        when(schoolRepository.getStudentsByQuantity()).thenReturn(students.size());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/getStudentsByQuantity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(expValue));
    }

    @Test
    public void getAverageAgeStudents() throws Exception {
        double expValue = 25.0;
        Faculty faculty = new Faculty(1L, "ur-fak", "red");
        List<Student> students = Arrays.asList(
                new Student(1L, "Sergey", 27, faculty),
                new Student(2L, "Petr", 23, faculty));

        Double averageValue = getAverageValue(students.get(0).getAge(), students.get(1).getAge());

        when(schoolRepository.getAverageAgeStudents()).thenReturn(averageValue);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/getAverageAgeStudents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(expValue));

    }

    @Test
    public void getLastFiveStudents() throws Exception {
        int expValue = 5;

        Faculty faculty = new Faculty(1L, "ur-fak", "red");

        List<Student> students = Arrays.asList(
                new Student(4L, "Aleksandr", 22, faculty),
                new Student(5L, "Ludmila", 21, faculty),
                new Student(6L, "Kirill", 23, faculty),
                new Student(7L, "Oleg", 22, faculty),
                new Student(8L, "Stanislav", 21, faculty));

        when(schoolRepository.getLastFiveStudents()).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/getLastFiveStudents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(expValue))
                .andExpect(jsonPath("$[0].id").value(4L))
                .andExpect(jsonPath("$[1].id").value(5L))
                .andExpect(jsonPath("$[2].id").value(6L))
                .andExpect(jsonPath("$[3].id").value(7L))
                .andExpect(jsonPath("$[4].id").value(8L));
    }

    private Double getAverageValue(int numOne, int numTwo) {
        return (Double) (double) ((numOne + numTwo) / 2);
    }
}
