package com.microservice.students.services;

import com.microservice.students.model.Course;

public interface CourseService {
    Course courseById(Long id);
}
