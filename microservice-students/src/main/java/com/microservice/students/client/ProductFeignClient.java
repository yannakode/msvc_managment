package com.microservice.students.client;

import com.microservice.students.model.Course;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-courses", url = "http://localhost:2222/api/courses/v1")
public interface ProductFeignClient {

    @GetMapping
    ResponseEntity<List<Course>> getAllCourses();

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id);

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody @Valid Course course);

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@RequestBody Long id, Course course);

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> updateCourse(@RequestBody Long id);
}
