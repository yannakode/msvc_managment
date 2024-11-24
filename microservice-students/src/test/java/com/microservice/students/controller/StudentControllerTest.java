package com.microservice.students.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.students.exceptions.StudentNotFoundException;
import com.microservice.students.model.dtos.StudentRequest;
import com.microservice.students.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.microservice.students.commons.StudentConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService studentService;

    @Test
    public void getAllStudents_ReturnsOk() throws Exception {
        when(studentService.getAll()).thenReturn(STUDENTS_RESPONSE);

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void getAllStudents_ReturnsOkWithNoStudents() throws Exception {
        when(studentService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk());
    }

    @Test
    public void getStudent_WithValidId_ReturnsOk() throws Exception {
        when(studentService.getById(1L)).thenReturn(STUDENT_RESPONSE);

        mockMvc.perform(get("/api/v1/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(STUDENT_RESPONSE.getName()));
    }

    @Test
    public void getStudent_WithInvalidId_ReturnsNotFound() throws Exception {
        when(studentService.getById(1L)).thenThrow(new StudentNotFoundException(1L));

        mockMvc.perform(get("/api/v1/students/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createStudent_WithValidData_ReturnsCreated() throws Exception {
        when(studentService.create(any(StudentRequest.class))).thenReturn(STUDENT_RESPONSE);

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(STUDENT_REQUEST)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(STUDENT_RESPONSE.getName()));
    }

    @Test
    public void createStudent_WithInvalidData_ReturnsNotFound() throws Exception {
        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(EMPTY_STUDENT_REQUEST)))
                .andExpect(status().isUnprocessableEntity());

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(INVALID_STUDENT_REQUEST)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void updateStudent_WithValidId_ReturnsOk() throws Exception {
        when(studentService.update(any(Long.class), any(StudentRequest.class))).thenReturn(STUDENT_RESPONSE);

        mockMvc.perform(put("/api/v1/students/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(STUDENT_REQUEST)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(STUDENT_RESPONSE.getName()));
    }

    @Test
    public void updateStudent_WithInvalidId_ReturnsNotFound() throws Exception {
        when(studentService.update(any(Long.class), any(StudentRequest.class))).thenThrow(StudentNotFoundException.class);

        mockMvc.perform(put("/api/v1/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(STUDENT_REQUEST)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateStudent_WithInvalidData_ReturnsUnprocessableEntity() throws Exception {
        mockMvc.perform(put("/api/v1/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(EMPTY_STUDENT_REQUEST)))
                .andExpect(status().isUnprocessableEntity());

        mockMvc.perform(put("/api/v1/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(INVALID_STUDENT_REQUEST)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void deleteStudent_WithValidId_ReturnsOk() throws Exception {
        mockMvc.perform(delete("/api/v1/students/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteStudent_WithInvalidId_ReturnsNotFound() throws Exception {
        doThrow(StudentNotFoundException.class).when(studentService).delete(any(Long.class));

        mockMvc.perform(delete("/api/v1/students/1"))
                .andExpect(status().isNotFound());
    }
}
