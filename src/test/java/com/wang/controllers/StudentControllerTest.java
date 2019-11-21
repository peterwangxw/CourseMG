package com.wang.controllers;

import com.wang.course.Application;
import com.wang.course.controllers.StudentController;
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
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterService service;

    @Autowired
    private StudentController studentController;

    @Test
    public void whenUserControllerInjected_thenNotNull() throws Exception {
        assertThat(studentController).isNotNull();
    }

    @Test
    public void whenGetRequestToStudent_thenCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/student")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void whenPostRequestToUsersAndValidStudent_thenCorrectResponse() throws Exception {
        MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"));
        String student = "{\"firstName\": \"Peter\", \"lastName\" : \"Wang\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/student")
                .content(student)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(textPlainUtf8));
    }

    @Test
    public void whenPostRequestToStudentsAndInValidStudent_thenCorrectReponse() throws Exception {
        String student = "{\"firstName\": \"\", \"lastName\" : \"Wang\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/student")
                .content(student)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is("FirstName is mandatory")))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    // TODO....
}
