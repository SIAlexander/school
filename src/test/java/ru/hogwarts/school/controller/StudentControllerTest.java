package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    StudentController studentController;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        Assertions
                .assertThat(studentController).isNotNull();
    }

    @Test
    public void getStudents() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotNull();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        String uri = "http://localhost:" + port + "/{student}";
        Map<String, String> params = new HashMap<>();
        params.put("student", "student");

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("studentId", 1L)
                .encode()
                .toUriString();


        ResponseEntity<String> response = restTemplate.exchange(
                urlTemplate,
                HttpMethod.GET,
                entity,
                String.class,
                params
        );
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void getFaculty() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        String uri = "http://localhost:" + port + "/student/{showStudentsFaculty}";
        Map<String, String> params = new HashMap<>();
        params.put("showStudentsFaculty", "showStudentsFaculty");

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("studentId", 1L)
                .encode()
                .toUriString();


        ResponseEntity<String> response = restTemplate.exchange(
                urlTemplate,
                HttpMethod.GET,
                entity,
                String.class,
                params
        );
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void updateStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Petr");
        student.setAge(23);

        HttpEntity<Student> entity = new HttpEntity<>(student);

        ResponseEntity<Student> responseEntity = restTemplate
                .exchange("http://localhost:" + port + "/student", HttpMethod.PUT, entity, Student.class, 1L);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody().getName(), is("Petr"));
        assertThat(responseEntity.getBody().getAge(), is(23));
    }

    @Test
    public void getStudentsByAge() throws Exception {
        int testAge = 23;
        int testMinAge = 20;
        int testMaxAge = 25;

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        String uri = "http://localhost:" + port + "/student/{findStudentsAge}";
        Map<String, String> params = new HashMap<>();
        params.put("findStudentsAge", "findStudentsAge");

        String urlTemplateAge = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("findStudentsAge", testAge)
                .encode()
                .toUriString();

        String urlTemplateMinMaxAge = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("minAge", testMinAge)
                .queryParam("maxAge", testMaxAge)
                .encode()
                .toUriString();

        ResponseEntity<String> responseAge = restTemplate.exchange(
                urlTemplateAge,
                HttpMethod.GET,
                entity,
                String.class,
                params
        );

        ResponseEntity<String> responseMinMaxAge = restTemplate.exchange(
                urlTemplateMinMaxAge,
                HttpMethod.GET,
                entity,
                String.class,
                params
        );
        assertThat(responseAge.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseMinMaxAge.getStatusCode(), is(HttpStatus.OK));
    }
}
