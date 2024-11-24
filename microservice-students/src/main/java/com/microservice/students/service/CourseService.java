package com.microservice.students.service;

import com.microservice.students.model.Course;

public interface CourseService {
    Course courseById(Long id);
}
