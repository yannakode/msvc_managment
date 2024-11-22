package com.microservice.students.service.Impl;

import com.microservice.students.client.ProductFeignClient;
import com.microservice.students.exceptions.StudentNotFoundException;
import com.microservice.students.model.Student;
import com.microservice.students.model.dtos.StudentRequest;
import com.microservice.students.model.dtos.StudentResponse;
import com.microservice.students.repository.StudentRepository;
import com.microservice.students.service.StudentService;
import com.microservice.students.service.assembler.StudentDtoAssembler;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final StudentDtoAssembler mapper;

    //private final ProductFeignClient productFeignClient;

    public StudentServiceImpl(StudentRepository studentRepository, StudentDtoAssembler mapper) {
        this.studentRepository = studentRepository;
        this.mapper = mapper;
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
}
