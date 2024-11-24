package com.microservice.students.exceptions;

public class StudentsByCourseNotFoundException extends RuntimeException{
    public StudentsByCourseNotFoundException(Long courseId){
        super("No students found for course id: " + courseId);
    }
}
