package com.microservice.students.exceptions;

public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(Long courseId){
        super("course not found with id: " + courseId);
    }


}
