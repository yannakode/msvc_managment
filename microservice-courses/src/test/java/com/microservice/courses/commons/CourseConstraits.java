package com.microservice.courses.commons;

import com.microservice.courses.model.Course;
import com.microservice.courses.model.dtos.CourseRequest;
import com.microservice.courses.model.dtos.CourseResponse;

import java.util.Arrays;
import java.util.List;

public class CourseConstraits {
    public static final List<Course> COURSES = Arrays.asList(
            new Course(1L, "Mathematics"),
            new Course(2L, "Science"),
            new Course(3L, "History")
    );

    public static final List<CourseResponse> COURSES_RESPONSE = Arrays.asList(
            new CourseResponse("Mathematics"),
            new CourseResponse("Science"),
            new CourseResponse("History")
    );

    public static final Course COURSE = new Course(1L, "Mathematics");
    public static final CourseResponse COURSE_RESPONSE = new CourseResponse("Mathematics");
    public static final CourseRequest COURSE_REQUEST = new CourseRequest("Mathematics");

    public static final CourseRequest EMPTY_COURSE_REQUEST = new CourseRequest();
    public static final CourseRequest INVALID_COURSE_REQUEST = new CourseRequest("");
}
