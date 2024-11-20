package com.microservice.courses.service.impl;

import com.microservice.courses.model.Course;
import com.microservice.courses.model.dtos.CourseRequest;
import com.microservice.courses.model.dtos.CourseResponse;
import com.microservice.courses.repository.CourseRepository;
import com.microservice.courses.service.CourseService;
import com.microservice.courses.service.assembler.MapStruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final MapStruct mapStruct;

    @Override
    public List<CourseResponse> getAll() {
        return courseRepository.findAll().stream()
                .map(mapStruct::toCourseResponse)
                .toList();
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return mapStruct.toCourseResponse(course);
    }

    @Override
    public CourseResponse create(CourseRequest courseRequest) {
        Course savedCourse = courseRepository.save(mapStruct.toCourse(courseRequest));
        return mapStruct.toCourseResponse(savedCourse);
    }

    @Override
    public CourseResponse update(Long id, CourseRequest courseRequest) {
        Course course = courseRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

            course.setName(courseRequest.name());
            return mapStruct.toCourseResponse(course);
    }

    @Override
    public void delete(Long id) {
        courseRepository.findById(id)
                .ifPresentOrElse(c -> courseRepository.deleteById(id),
                        EntityNotFoundException::new);
    }
}
