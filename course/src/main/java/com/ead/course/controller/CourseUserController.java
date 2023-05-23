package com.ead.course.controller;

import com.ead.course.services.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Slf4j
@RestController
@RequestMapping("api/courseUser")
@CrossOrigin("*")
public class CourseUserController {

    private final CourseService courseService;

    public CourseUserController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("courses/{courseId}/users")
    public ResponseEntity<?> getAllUsersByCourse(@PathVariable("courseId") BigInteger courseId) {
        return ResponseEntity.ok("");
    }

    @GetMapping("studentsNotIn/course/{courseId}")
    public ResponseEntity<?> getStudentsNotIn(@PathVariable("courseId") BigInteger courseId) {
        return ResponseEntity.ok("");
    }


}
