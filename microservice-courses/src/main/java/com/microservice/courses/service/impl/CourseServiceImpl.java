package com.microservice.courses.service.impl;

import com.microservice.courses.model.Course;
import com.microservice.courses.model.dtos.CourseRequest;
import com.microservice.courses.model.dtos.CourseResponse;
import com.microservice.courses.repository.CourseRepository;
import com.microservice.courses.service.CourseService;
import com.microservice.courses.service.assembler.CourseDtoAssembler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseDtoAssembler mapper;

    @Override
    public List<CourseResponse> getAll() {
        return courseRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return mapper.toDto(course);
    }

    @Override
    public CourseResponse create(CourseRequest courseRequest) {

        Course course = mapper.toEntity(courseRequest);

        Course savedCourse = courseRepository.save(course);

        return mapper.toDto(savedCourse);
    }

    @Override
    public CourseResponse update(Long id, CourseRequest courseRequest) {
        Course course = courseRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

            course.setName(courseRequest.getName());
            return mapper.toDto(course);
    }

    @Override
    public void delete(Long id) {
        courseRepository.findById(id)
                .ifPresentOrElse(c -> courseRepository.deleteById(id),
                        EntityNotFoundException::new);
    }
}
