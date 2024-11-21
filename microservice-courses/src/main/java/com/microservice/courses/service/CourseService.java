package com.microservice.courses.service;

import com.microservice.courses.model.dtos.CourseRequest;
import com.microservice.courses.model.dtos.CourseResponse;

import java.util.List;

public interface CourseService {
    List<CourseResponse> getAll();
    CourseResponse getCourseById(Long id);
    CourseResponse create(CourseRequest courseRequest);
    CourseResponse update(Long id, CourseRequest courseRequest);
    void delete(Long id);
}
