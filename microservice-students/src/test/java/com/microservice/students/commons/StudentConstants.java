package com.microservice.students.commons;

import com.microservice.students.model.Student;
import com.microservice.students.model.dtos.StudentRequest;
import com.microservice.students.model.dtos.StudentResponse;

import java.util.Arrays;
import java.util.List;

public class StudentConstants {
    public static final List<Student> STUDENTS = Arrays.asList(
            new Student(1L, "Maria", "Silva", "25", "maria@example.com", "6"),
            new Student(2L, "João", "Oliveira", "22", "joao@example.com", "1"),
            new Student(3L, "Paola", "Vasconcelos", "30", "paola@example.com", "2")
    );

    public static final List<StudentResponse> STUDENTS_RESPONSE = Arrays.asList(
            new StudentResponse("Maria", "Silva", "25", "maria@example.com", "6"),
            new StudentResponse("João", "Oliveira", "22", "joao@example.com", "1"),
            new StudentResponse("Paola", "Vasconcelos", "30", "paola@example.com", "2")
    );

    public static final Student STUDENT = new Student(1L, "Maria", "Silva", "25", "maria@example.com", "6");
    public static final StudentResponse STUDENT_RESPONSE = new StudentResponse("Maria", "Silva", "25", "maria@example.com", "6");
    public static final StudentRequest STUDENT_REQUEST = new StudentRequest("Maria", "Silva", "25", "maria@example.com", "6");

    public static final StudentRequest EMPTY_STUDENT_REQUEST = new StudentRequest();
    public static final StudentRequest INVALID_STUDENT_REQUEST = new StudentRequest("", "", "", "", "");
}
