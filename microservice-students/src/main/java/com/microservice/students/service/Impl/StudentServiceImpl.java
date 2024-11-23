package com.microservice.students.service.Impl;

import com.microservice.students.client.CourseFeignClient;
import com.microservice.students.exceptions.StudentNotFoundException;
import com.microservice.students.model.Course;
import com.microservice.students.model.Student;
import com.microservice.students.model.dtos.StudentRequest;
import com.microservice.students.model.dtos.StudentResponse;
import com.microservice.students.model.dtos.StudentsByCourseResponse;
import com.microservice.students.repository.StudentRepository;
import com.microservice.students.service.StudentService;
import com.microservice.students.service.assembler.StudentDtoAssembler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final StudentDtoAssembler mapper;

    private final CourseFeignClient courseFeignClient;

    public StudentServiceImpl(StudentRepository studentRepository, StudentDtoAssembler mapper, CourseFeignClient courseFeignClient) {
        this.studentRepository = studentRepository;
        this.mapper = mapper;
        this.courseFeignClient = courseFeignClient;
    }

    @Override
    public List<StudentResponse> getAll() {
        return studentRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public StudentResponse getById(Long id) {
        return studentRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(()-> new StudentNotFoundException(id));
    }

    @Override
    public StudentResponse create(StudentRequest studentRequest) {
        Student student = mapper.toEntity(studentRequest);
        Student createdStudent = studentRepository.save(student);
        return mapper.toDto(createdStudent);
    }

    @Override
    public StudentResponse update(Long id, StudentRequest studentRequest) {
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new StudentNotFoundException(id));

        student.setName(studentRequest.getName());
        student.setLastName(studentRequest.getLastName());
        student.setAge(studentRequest.getAge());
        student.setCourseId(studentRequest.getCourseId());

        return mapper.toDto(student);
    }

    @Override
    public void delete(Long id) {
        studentRepository.findById(id)
                .ifPresentOrElse(student -> studentRepository.deleteById(id),
                        ()-> {
                            throw new StudentNotFoundException(id);
                        });
    }

    @Override
    public StudentsByCourseResponse studentsByCourse(Long courseId) {
        Course course = courseFeignClient.getCourseById(courseId).getBody();
        List<StudentResponse> studentsByCourseId = studentRepository.findByCourseId(courseId.toString()).stream()
                .map(mapper::toDto)
                .toList();

        if(studentsByCourseId.isEmpty()) throw new StudentNotFoundException(courseId);
        StudentsByCourseResponse studentsByCourseResponse = new StudentsByCourseResponse();
        studentsByCourseResponse.setCourse(course);
        studentsByCourseResponse.setStudentResponse(studentsByCourseId);
        return studentsByCourseResponse;
    }
}
