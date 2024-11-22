package com.microservice.students.service;

import com.microservice.students.model.dtos.StudentRequest;
import com.microservice.students.model.dtos.StudentResponse;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<StudentResponse> getAll();
    StudentResponse getById(Long id);
    StudentResponse create(StudentRequest studentRequest);
    StudentResponse update(Long id, StudentRequest studentRequest);
    void delete(Long id);
}