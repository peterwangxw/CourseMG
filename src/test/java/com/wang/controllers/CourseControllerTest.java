package com.wang.controllers;


import com.wang.course.Application;
import com.wang.course.controllers.CourseController;
import com.wang.course.services.RegisterService;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
//@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterService service;

    @Autowired
    private CourseController courseController;

    @Test
    public void whenCourseControllerInjected_thenNotNull() throws Exception {
        assertThat(courseController).isNotNull();
    }

    @Test
    public void whenGetRequestToCourse_thenCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/course")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));

    }

    @Test
    public void whenPostRequestToCourseAndValidCourse_thenCorrectResponse() throws Exception {
        MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"));
        String course = "{\"name\": \"Math\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/course")
                .content(course)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(textPlainUtf8));
    }

    @Test
    public void whenPostRequestToCoursesAndInValidCourse_thenCorrectReponse() throws Exception {
        String course = "{\"name\": \"\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/course")
                .content(course)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is("Name is mandatory")))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    // TODO....
}
