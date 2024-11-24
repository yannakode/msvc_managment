package com.microservice.students.services.impl;

import com.microservice.students.exceptions.StudentNotFoundException;
import com.microservice.students.exceptions.StudentsByCourseNotFoundException;
import com.microservice.students.model.Student;
import com.microservice.students.model.dtos.StudentRequest;
import com.microservice.students.model.dtos.StudentResponse;
import com.microservice.students.model.dtos.StudentsByCourseResponse;
import com.microservice.students.repository.StudentRepository;
import com.microservice.students.services.CourseService;
import com.microservice.students.services.Impl.StudentServiceImpl;
import com.microservice.students.services.assembler.DtoAssembler;
import com.microservice.students.services.assembler.DtoAssemblerTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.microservice.students.commons.CourseConstants.COURSE;
import static com.microservice.students.commons.CourseConstants.STUDENTS_BY_COURSE_RESPONSE;
import static com.microservice.students.commons.StudentConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private DtoAssembler mapper;

    @Mock
    StudentRepository studentRepository;

    @Mock
    CourseService courseService;

    @Test
    public void getAll_ReturnsCoursesList(){
        when(studentRepository.findAll()).thenReturn(STUDENTS);
        when(mapper.toDto(any(Student.class))).thenReturn(STUDENT_RESPONSE);

        List<StudentResponse> listStudents = studentService.getAll();

        assertFalse(listStudents.isEmpty());
        assertEquals(listStudents.size(), 3);
    }

    @Test
    public void getCourse_WithValidId_ReturnsCourseResponse(){
        when(studentRepository.findById(1L)).thenReturn(Optional.of(STUDENT));
        when(mapper.toDto(any(Student.class))).thenReturn(STUDENT_RESPONSE);

        StudentResponse studentResponse = studentService.getById(1L);

        assertEquals(studentResponse, STUDENT_RESPONSE);
        verify(studentRepository, times(1)).findById(1L);
        verify(mapper, times(1)).toDto(STUDENT);
    }

    @Test
    public void getCourse_WithInvalidId_ReturnsStudentNotFoundException(){
        when(studentRepository.findById(1L)).thenThrow(StudentNotFoundException.class);

        assertThrows(StudentNotFoundException.class, ()->{studentService.getById(1L);});
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    public void createStudent_WithValidData_ReturnsStudentResponse(){
        when(mapper.toEntity(any(StudentRequest.class))).thenReturn(STUDENT);
        when(studentRepository.save(any(Student.class))).thenReturn(STUDENT);
        when(mapper.toDto(any(Student.class))).thenReturn(STUDENT_RESPONSE);

        StudentResponse studentResponse = studentService.create(STUDENT_REQUEST);

        assertEquals(studentResponse, STUDENT_RESPONSE);
        verify(studentRepository, times(1)).save(STUDENT);
        verify(mapper, times(1)).toDto(STUDENT);
        verify(mapper, times(1)).toEntity(STUDENT_REQUEST);
    }

    @Test
    public void updateStudent_WithValidId_ReturnsStudentResponse(){
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(STUDENT));
        when(mapper.toDto(any(Student.class))).thenReturn(STUDENT_RESPONSE);

        StudentResponse studentResponse = studentService.update(1L, STUDENT_REQUEST);

        assertEquals(studentResponse, STUDENT_RESPONSE);
        verify(studentRepository, times(1)).findById(1L);
        verify(mapper, times(1)).toDto(STUDENT);
    }

    @Test
    public void deleteStudent_WithValidId_ReturnsNoContent(){
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(STUDENT));

        studentService.delete(1L);

        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    public void deleteStudent_WithInvalidId_ReturnsException(){
        when(studentRepository.findById(any(Long.class))).thenThrow(StudentNotFoundException.class);

        assertThrows(StudentNotFoundException.class, ()-> {studentService.delete(1L);});
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    public void studentsByCourse_WithExistingCourse_ReturnsStudentsByCourse(){
        when(courseService.courseById(any(Long.class))).thenReturn(COURSE);
        when(studentRepository.findByCourseId(any(String.class))).thenReturn(STUDENTS);
        when(mapper.toDto(any(Student.class))).thenReturn(STUDENT_RESPONSE);

        StudentsByCourseResponse studentsByCourseResponse = studentService.studentsByCourse(1L);

        assertEquals(studentsByCourseResponse.getCourse(), STUDENTS_BY_COURSE_RESPONSE.getCourse());
        assertEquals(studentsByCourseResponse.getStudentResponseList().get(0).getName(), STUDENTS_RESPONSE.get(0).getName());
    }

    @Test
    public void studentsByCourse_WithUnexistingCourse_ReturnsStudentsByCourseNotFoundException(){
        when(courseService.courseById(any(Long.class))).thenReturn(COURSE);
        when(studentRepository.findByCourseId(any(String.class)))
                .thenThrow(StudentsByCourseNotFoundException.class);

        assertThrows(StudentsByCourseNotFoundException.class,
                ()-> studentService.studentsByCourse(1L));
    }
}
