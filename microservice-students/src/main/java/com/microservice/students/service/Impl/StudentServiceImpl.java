package com.microservice.students.service.Impl;

import com.microservice.students.model.dtos.StudentRequest;
import com.microservice.students.model.dtos.StudentResponse;
import com.microservice.students.repository.StudentRepository;
import com.microservice.students.service.StudentService;
import com.microservice.students.service.assembler.StudentDtoAssembler;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private StudentDtoAssembler mapper;

    public StudentServiceImpl(StudentRepository studentRepository, StudentDtoAssembler mapper) {
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    @Override
    public List<StudentResponse> getAll() {
        return studentRepository.findAll().stream()
                .map(student -> mapper.toDto(student))
                .toList();
    }

    @Override
    public Optional<StudentResponse> getById(Long id) {
        return Optional.ofNullable(studentRepository.findById(id)
                .map(student -> mapper.toDto(student))
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public StudentResponse create(StudentRequest studentRequest) {
        return null;
    }

    @Override
    public StudentResponse update(Long id, StudentRequest studentRequest) {
        return null;
    }

    @Override
    public void delete(Long id) {
        studentRepository.findById(id)
                .ifPresentOrElse(student -> studentRepository.deleteById(id),
                        EntityNotFoundException::new);
    }
}
