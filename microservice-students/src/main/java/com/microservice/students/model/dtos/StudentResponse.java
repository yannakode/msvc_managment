package com.microservice.students.model.dtos;

import com.microservice.students.model.Course;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {

    private String name;

    private String lastName;

    private String age;

    private String email;

    private String courseId;
}
