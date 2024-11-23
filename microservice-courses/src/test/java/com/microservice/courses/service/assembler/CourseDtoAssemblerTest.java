package com.microservice.courses.service.assembler;

import com.microservice.courses.model.Course;
import com.microservice.courses.model.dtos.CourseResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static com.microservice.courses.commons.CourseConstraits.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseDtoAssemblerTest {
    @InjectMocks
    CourseDtoAssembler mapper;

    @Mock
    ModelMapper modelMapper;

    @Test
    public void toDto_ShouldMapCourseToCourseResponse(){
        when(modelMapper.map(COURSE, CourseResponse.class)).thenReturn(COURSE_RESPONSE);

        CourseResponse courseResponse = mapper.toDto(COURSE);

        assertEquals(courseResponse.getName(), COURSE.getName());
    }

    @Test
    public void toEntity_ShouldMapCourseRequestToCourse(){
        when(modelMapper.map(COURSE_REQUEST, Course.class)).thenReturn(COURSE);

        Course course = mapper.toEntity(COURSE_REQUEST);

        assertEquals(course.getName(), COURSE_REQUEST.getName());
    }
}
