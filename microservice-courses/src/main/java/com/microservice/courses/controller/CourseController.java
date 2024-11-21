package com.microservice.courses.controller;

import com.microservice.courses.model.dtos.CourseRequest;
import com.microservice.courses.model.dtos.CourseResponse;
import com.microservice.courses.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses/v1")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourses(){
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourseById(id));
    }

    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@RequestBody @Valid CourseRequest courseRequest){
        System.out.println(courseRequest.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.create(courseRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> updateCourse(@RequestBody Long id, CourseRequest courseRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.update(id, courseRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> updateCourse(@RequestBody Long id){
        courseService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
