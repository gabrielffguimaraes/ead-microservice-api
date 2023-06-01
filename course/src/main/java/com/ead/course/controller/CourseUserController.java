package com.ead.course.controller;

import com.ead.course.services.CourseService;
import com.ead.course.services.UserService;
import com.ead.course.specification.CourseSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/courseUser")
@CrossOrigin("*")
public class CourseUserController {

    private final UserService userService;
    private final CourseService courseService;

    public CourseUserController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping("courses/{courseId}/users")
    public ResponseEntity<?> getAllUsersByCourse(@PathVariable("courseId") UUID courseId) {
        return ResponseEntity.ok(userService.findAll(CourseSpecification.filterUserId(courseId)));
    }

    @GetMapping("studentsNotIn/course/{courseId}")
    public ResponseEntity<?> getStudentsNotIn(@PathVariable("courseId") BigInteger courseId) {
        return ResponseEntity.ok("");
    }


}
