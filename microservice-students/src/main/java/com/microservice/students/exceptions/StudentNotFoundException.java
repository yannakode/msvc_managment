package com.microservice.students.exceptions;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(Long studentId){
        super("Student with id " + studentId + "not found");
    }
}
