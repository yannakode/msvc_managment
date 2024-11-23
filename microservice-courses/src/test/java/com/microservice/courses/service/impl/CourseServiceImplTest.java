package com.microservice.courses.service.impl;

import com.microservice.courses.exceptions.CourseNotFoundException;
import com.microservice.courses.model.Course;
import com.microservice.courses.model.dtos.CourseRequest;
import com.microservice.courses.model.dtos.CourseResponse;
import com.microservice.courses.repository.CourseRepository;
import com.microservice.courses.service.assembler.CourseDtoAssembler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.microservice.courses.commons.CourseConstraits.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {

    @InjectMocks
    CourseServiceImpl courseService;

    @Mock
    CourseRepository courseRepository;

    @Mock
    CourseDtoAssembler mapper;

    @Test
    public void getAllCourses_ReturnsCourses(){
        when(courseRepository.findAll()).thenReturn(COURSES);
        when(mapper.toDto(any(Course.class))).thenReturn(COURSE_RESPONSE);

        List<CourseResponse> courseResponseList = courseService.getAll();

        assertFalse(courseResponseList.isEmpty());
        assertEquals(courseResponseList.size(), 3);
        assertEquals(courseResponseList.get(0), COURSE_RESPONSE);

        verify(courseRepository, times(1)).findAll();
    }

    @Test
    public void getCourseById_WithValidId_ReturnsCourseResponse(){
        when(courseRepository.findById(1L)).thenReturn(Optional.of(COURSE));
        when(mapper.toDto(any(Course.class))).thenReturn(COURSE_RESPONSE);

        CourseResponse courseResponse = courseService.getCourseById(1L);

        assertNotNull(courseResponse);
        assertEquals(courseResponse, COURSE_RESPONSE);
    }

    @Test
    public void getCourseById_WithInvalidId_ReturnsCourseResponse(){
        when(courseRepository.findById(1L)).thenThrow(CourseNotFoundException.class);

        assertThrows(CourseNotFoundException.class, ()->{
            courseService.getCourseById(1L);
        });
    }

    @Test
    public void createCourse_WithValidData_ReturnsCourseResponse(){
        when(mapper.toEntity(any(CourseRequest.class))).thenReturn(COURSE);
        when(courseRepository.save(COURSE)).thenReturn(COURSE);
        when(mapper.toDto(any(Course.class))).thenReturn(COURSE_RESPONSE);

        CourseResponse courseCreated = courseService.create(COURSE_REQUEST);

        assertEquals(courseCreated, COURSE_RESPONSE);
        verify(mapper, times(1)).toDto(COURSE);
        verify(mapper, times(1)).toEntity(COURSE_REQUEST);
        verify(courseRepository, times(1)).save(COURSE);
    }

    @Test
    public void updateCourse_WithValidData_ReturnsCourse(){
        when(courseRepository.findById(1L)).thenReturn(Optional.of(COURSE));
        when(mapper.toDto(any(Course.class))).thenReturn(COURSE_RESPONSE);

        CourseResponse updatedCourse = courseService.update(1L, COURSE_REQUEST);

        assertEquals(updatedCourse.getName(), COURSE_RESPONSE.getName());
    }

    @Test
    public void updateCourse_WithUnexistingId_ReturnsCourse(){
        when(courseRepository.findById(1L)).thenThrow(CourseNotFoundException.class);

        assertThrows(CourseNotFoundException.class, ()->{
            courseService.update(1L, COURSE_REQUEST);
        });
    }

    @Test
    public void deleteCourse_WithValidId(){
        when(courseRepository.findById(1L)).thenReturn(Optional.of(COURSE));

        courseService.delete(1L);

        verify(courseRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).deleteById(1L);
    }
}
