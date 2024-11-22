package com.microservice.students.model.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {

    @NotBlank(message = "Name field cannot be blank or null")
    @Size(min = 2, message = "Name must have at least 2 characters")
    private String name;

    @NotBlank(message = "Last name field cannot be blank or null")
    @Size(min = 2, message = "Name must have at least 2 characters")
    private String lastName;

    @NotBlank(message = "Age field name field cannot be blank or null")
    @Size(min = 1, max = 3, message = "Age field must have at least 2 characters")
    private String age;

    @Email(message = "Invalid email format")
    private String email;

    private String courseId;
}
