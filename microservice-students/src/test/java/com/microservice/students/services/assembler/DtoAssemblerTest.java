package com.microservice.students.services.assembler;

import com.microservice.students.model.Student;
import com.microservice.students.model.dtos.StudentResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static com.microservice.students.commons.CourseConstants.COURSE;
import static com.microservice.students.commons.StudentConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DtoAssemblerTest {
    @InjectMocks
    DtoAssembler mapper;

    @Mock
    ModelMapper modelMapper;

    @Test
    public void toDto_ShouldMapStudentToStudentResponse(){
        when(modelMapper.map(STUDENT, StudentResponse.class)).thenReturn(STUDENT_RESPONSE);

        StudentResponse studentResponse = mapper.toDto(STUDENT);

        assertEquals(studentResponse.getName(), STUDENT.getName());
    }

    @Test
    public void toEntity_ShouldMapCourseRequestToCourse(){
        when(modelMapper.map(STUDENT_REQUEST, Student.class)).thenReturn(STUDENT);

        Student student = mapper.toEntity(STUDENT_REQUEST);

        assertEquals(student.getName(), STUDENT_REQUEST.getName());
    }
}
