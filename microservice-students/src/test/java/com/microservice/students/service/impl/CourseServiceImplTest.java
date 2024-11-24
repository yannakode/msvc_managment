package com.microservice.students.service.impl;

import com.microservice.students.client.CourseFeignClient;
import com.microservice.students.exceptions.CourseNotFoundException;
import com.microservice.students.model.Course;
import com.microservice.students.service.CourseService;
import com.microservice.students.service.Impl.CourseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.microservice.students.commons.CourseConstants.COURSE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {

    @InjectMocks
    CourseServiceImpl courseService;

    @Mock
    CourseFeignClient courseFeignClient;

    @Test
    public void courseById_WithValidId_ReturnsCourse(){
        when(courseFeignClient.getCourseById(anyLong())).thenReturn(COURSE);

        Course course = courseService.courseById(1L);

        assertEquals(course, COURSE);
    }

    @Test
    public void courseById_WithInvalidId_ReturnsCourseNotFoundException(){
        when(courseFeignClient.getCourseById(anyLong())).thenThrow(CourseNotFoundException.class);

        assertThrows(CourseNotFoundException.class, ()-> courseService.courseById(1L));
    }
}
