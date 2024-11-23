package com.microservice.courses.service.assembler;

import com.microservice.courses.model.Course;
import com.microservice.courses.model.dtos.CourseRequest;
import com.microservice.courses.model.dtos.CourseResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CourseDtoAssembler {
    private final ModelMapper modelMapper;

    public CourseResponse toDto(Course course){
        return modelMapper.map(course, CourseResponse.class);
    }

    public Course toEntity(CourseRequest courseRequest){
        return modelMapper.map(courseRequest, Course.class);
    }
}