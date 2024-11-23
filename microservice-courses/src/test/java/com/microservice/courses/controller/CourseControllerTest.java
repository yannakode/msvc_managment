package com.microservice.courses.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.courses.exceptions.CourseNotFoundException;
import com.microservice.courses.model.dtos.CourseRequest;
import com.microservice.courses.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.microservice.courses.commons.CourseConstraits.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CourseController.class)
public class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CourseService courseService;

    @Test
    public void getAllCourses_ReturnsOk() throws Exception {
        when(courseService.getAll()).thenReturn(COURSES_RESPONSE);

        mockMvc.perform(get("/api/courses/v1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void getAllCourses_ReturnsOkWithNoCourses() throws Exception {
        when(courseService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/courses/v1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getCourse_WithValidId_ReturnsOk() throws Exception {
        when(courseService.getCourseById(1L)).thenReturn(COURSE_RESPONSE);

        mockMvc.perform(get("/api/courses/v1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(COURSE_RESPONSE.getName()));
    }

    @Test
    public void getCourse_WithInvalidId_ReturnsNotFound() throws Exception {
        when(courseService.getCourseById(1L)).thenThrow(new CourseNotFoundException(1L));

        mockMvc.perform(get("/api/courses/v1/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createCourse_WithValidData_ReturnsCreated() throws Exception {
        when(courseService.create(any(CourseRequest.class))).thenReturn(COURSE_RESPONSE);

        mockMvc.perform(post("/api/courses/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(COURSE_REQUEST)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(COURSE_RESPONSE.getName()));

    }

    @Test
    public void createCourse_WithInvalidData_ReturnsNotFound() throws Exception {
        mockMvc.perform(post("/api/courses/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(EMPTY_COURSE_REQUEST)))
                .andExpect(status().isUnprocessableEntity());

        mockMvc.perform(post("/api/courses/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(INVALID_COURSE_REQUEST)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void updateCourse_WithValidId_ReturnsOk() throws Exception {
        when(courseService.update(any(Long.class), any(CourseRequest.class))).thenReturn(COURSE_RESPONSE);

        mockMvc.perform(put("/api/courses/v1/1").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(COURSE_REQUEST)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(COURSE_RESPONSE.getName()));
    }

    @Test
    public void updateCourse_WithInvalidId_ReturnsNotFound() throws Exception {
        when(courseService.update(any(Long.class), any(CourseRequest.class))).thenThrow(CourseNotFoundException.class);

        mockMvc.perform(put("/api/courses/v1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(COURSE_REQUEST)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateCourse_WithInvalidData_ReturnsUnprocessableEntity() throws Exception {
        mockMvc.perform(put("/api/courses/v1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(EMPTY_COURSE_REQUEST)))
                .andExpect(status().isUnprocessableEntity());

        mockMvc.perform(put("/api/courses/v1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(INVALID_COURSE_REQUEST)))
                .andExpect(status().isUnprocessableEntity());
    }
    @Test
    public void deleteCourse_WithValidId_ReturnsOk() throws Exception {
        mockMvc.perform(delete("/api/courses/v1/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteCourse_WithInvalidId_ReturnsNotFound() throws Exception {
        doThrow(CourseNotFoundException.class).when(courseService).delete(any(Long.class));

        mockMvc.perform(delete("/api/courses/v1/1"))
                .andExpect(status().isNotFound());
    }
}
