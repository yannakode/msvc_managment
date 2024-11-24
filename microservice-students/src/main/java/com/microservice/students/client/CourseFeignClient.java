package com.microservice.students.client;

import com.microservice.students.model.Course;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-courses", url = "http://localhost:2222/api/courses/v1")
public interface CourseFeignClient {

    @GetMapping
    List<Course> getAllCourses();

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Long id);

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody @Valid Course course);

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable Long id, @RequestBody Course course);

    @DeleteMapping("/{id}")
    public Void deleteCourse(@PathVariable Long id);
}
