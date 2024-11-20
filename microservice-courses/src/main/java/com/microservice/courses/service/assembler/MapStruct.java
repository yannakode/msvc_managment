package com.microservice.courses.service.assembler;

import com.microservice.courses.model.Course;
import com.microservice.courses.model.dtos.CourseRequest;
import com.microservice.courses.model.dtos.CourseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MapStruct {
    MapStruct INSTANCE = Mappers.getMapper(MapStruct.class);

    Course toCourse(CourseRequest courseRequest);
    CourseResponse toCourseResponse(Course course);
}
