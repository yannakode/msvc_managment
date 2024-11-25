package com.microservice.students.commons;

import com.microservice.students.model.Course;
import com.microservice.students.model.dtos.StudentsByCourseResponse;

import java.util.List;

import static com.microservice.students.commons.StudentConstants.STUDENTS_RESPONSE;
import static com.microservice.students.commons.StudentConstants.STUDENT_RESPONSE;

public class CourseConstants {
    public static final Course COURSE = new Course("Mathematics");

    public static final List<Course> COURSES = List.of(
            COURSE
    );

    public static final StudentsByCourseResponse STUDENTS_BY_COURSE_RESPONSE = new StudentsByCourseResponse(COURSE, STUDENTS_RESPONSE);
}
