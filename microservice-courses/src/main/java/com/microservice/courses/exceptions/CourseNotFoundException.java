package com.microservice.courses.exceptions;

public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(Long courseId){
        super("Course with " + courseId + " not found");
    }
}
