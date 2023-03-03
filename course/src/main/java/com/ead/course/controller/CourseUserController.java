package com.ead.course.controller;

import com.ead.course.clients.AuthuserClient;
import com.ead.course.dto.SubscriptionDto;
import com.ead.course.dto.UserDto;
import com.ead.course.models.Course;
import com.ead.course.models.CourseUser;
import com.ead.course.services.CourseService;
import com.ead.course.services.CourseUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigInteger;

@Slf4j
@RestController
@RequestMapping("api/courseUser")
@CrossOrigin("*")
public class CourseUserController {

    private final CourseService courseService;
    private final CourseUserService courseUserService;
    @Autowired
    public AuthuserClient authuserClient;

    public CourseUserController(CourseService courseService, CourseUserService courseUserService) {
        this.courseService = courseService;
        this.courseUserService = courseUserService;
    }

    @GetMapping("courses/{courseId}/users")
    public ResponseEntity<?> getAllUsersByCourse(@PathVariable("courseId") BigInteger courseId) {
        return authuserClient.getAllUsersByCourse(courseId);
    }

    @GetMapping("studentsNotIn/course/{courseId}")
    public ResponseEntity<?> getStudentsNotIn(@PathVariable("courseId") BigInteger courseId) {
        return authuserClient.getAllStudentsNotInCourse(courseId);
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<?> saveSubscriptionUserInCourse(@PathVariable("courseId") BigInteger courseId, @RequestBody @Valid SubscriptionDto subscriptionDto) {
        log.info("CAINDO AQUI");
        ResponseEntity<UserDto> responseUser;
        Course course = this.courseService.findCourseById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"curso não encontrado"));
        if(this.courseUserService.existsByCourseAndUserId(course,subscriptionDto.getUserId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Error : subscription already exist");
        }
        try {
            responseUser =  authuserClient.getOneUserById(subscriptionDto.getUserId());
            this.authuserClient.saveSubscriptionUserInCourse(subscriptionDto.getUserId(),courseId);
        } catch (HttpStatusCodeException e) {
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error : user not found in database.");
            }
        }
        courseUserService.save(course.convertToCourseUser(subscriptionDto.getUserId()));
        return ResponseEntity.status(HttpStatus.CREATED).body("Subscription created successfully.");
    }
}
