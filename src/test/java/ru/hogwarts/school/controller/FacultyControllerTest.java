package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        Assertions
                .assertThat(facultyController).isNotNull();
    }

    @Test
    public void getFaculty() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty", String.class))
                .isNotNull();


        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        String uri = "http://localhost:" + port + "/{faculty}";
        Map<String, String> params = new HashMap<>();
        params.put("faculty", "faculty");

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("facultyId", 1L)
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
    public void getStudentFaculty() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        String uri = "http://localhost:" + port + "/faculty/{showStudentFaculty}";
        Map<String, String> params = new HashMap<>();
        params.put("showStudentFaculty", "showStudentFaculty");

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
    public void updateFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("ur-fak");
        faculty.setColor("red");

        HttpEntity<Faculty> entity = new HttpEntity<>(faculty);

        ResponseEntity<Faculty> responseEntity = restTemplate
                .exchange("http://localhost:" + port + "/faculty", HttpMethod.PUT, entity, Faculty.class, 1L);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody().getName(), is("ur-fak"));
        assertThat(responseEntity.getBody().getColor(), is("red"));
    }

    @Test
    public void getFacultyByColorName() throws Exception {

        String testColor = "red";
        String testName = "mat-fak";

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        String uri = "http://localhost:" + port + "/faculty/{findFaculty}";
        Map<String, String> params = new HashMap<>();
        params.put("findFaculty", "findFaculty");

        String urlTemplateColor = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("color", testColor)
                .encode()
                .toUriString();

        String urlTemplateName = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("name", testName)
                .encode()
                .toUriString();

        ResponseEntity<String> responseColor = restTemplate.exchange(
                urlTemplateColor,
                HttpMethod.GET,
                entity,
                String.class,
                params
        );

        ResponseEntity<String> responseName = restTemplate.exchange(
                urlTemplateName,
                HttpMethod.GET,
                entity,
                String.class,
                params
        );
        assertThat(responseColor.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseName.getStatusCode(), is(HttpStatus.OK));
    }

}
