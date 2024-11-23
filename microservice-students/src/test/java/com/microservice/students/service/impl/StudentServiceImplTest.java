package com.microservice.students.service.impl;

import com.microservice.students.client.CourseFeignClient;
import com.microservice.students.model.Student;
import com.microservice.students.model.dtos.StudentResponse;
import com.microservice.students.repository.StudentRepository;
import com.microservice.students.service.Impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;

import static com.microservice.students.commons.StudentConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private ModelMapper mapper;

    @Mock
    StudentRepository studentRepository;

    @Mock
    CourseFeignClient courseFeignClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAll_ReturnsCoursesList(){
        when(studentRepository.findAll()).thenReturn(STUDENTS);
        when(mapper.map(Student.class, StudentResponse.class)).thenReturn(STUDENT_RESPONSE);

        List<StudentResponse> listStudents = studentService.getAll();

        assertEquals(listStudents.size(), 3);
    }
}
