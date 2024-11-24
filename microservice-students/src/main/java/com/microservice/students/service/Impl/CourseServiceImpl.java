package com.microservice.students.service.Impl;

import com.microservice.students.client.CourseFeignClient;
import com.microservice.students.exceptions.CourseNotFoundException;
import com.microservice.students.model.Course;
import com.microservice.students.service.CourseService;
import feign.FeignException;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseFeignClient courseFeignClient;

    public CourseServiceImpl(CourseFeignClient courseFeignClient) {
        this.courseFeignClient = courseFeignClient;
    }

    @Override
    public Course courseById(Long id) {
        try{
            return courseFeignClient.getCourseById(id);
        }catch (FeignException.NotFound ex){
            throw new CourseNotFoundException(id);
        }
    }
}
