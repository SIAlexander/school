package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SchoolControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    SchoolController schoolController;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() throws Exception {
        Assertions
                .assertThat(schoolController).isNotNull();
    }

    @Test
    public void getStudentsByQuantity(){
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/getStudentsByQuantity", Integer.class))
                .isNotNull();
    }

    @Test
    public void getAverageAgeStudents(){
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/getAverageAgeStudents", Double.class))
                .isNotNull();
    }

    @Test
    public void getLastFiveStudents(){
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/getLastFiveStudents", String.class))
                .isNotNull();
    }
}
