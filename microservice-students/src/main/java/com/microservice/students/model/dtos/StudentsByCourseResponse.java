package com.microservice.students.model.dtos;

import com.microservice.students.model.Course;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentsByCourseResponse {
    private Course course;

    private List<StudentResponse> studentResponse;
}
