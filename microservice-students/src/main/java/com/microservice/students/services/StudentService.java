package com.microservice.students.services;

import com.microservice.students.model.dtos.StudentRequest;
import com.microservice.students.model.dtos.StudentResponse;
import com.microservice.students.model.dtos.StudentsByCourseResponse;

import java.util.List;

public interface StudentService {
    List<StudentResponse> getAll();
    StudentResponse getById(Long id);
    StudentResponse create(StudentRequest studentRequest);
    StudentResponse update(Long id, StudentRequest studentRequest);
    void delete(Long id);
    StudentsByCourseResponse studentsByCourse(Long courseId);
}