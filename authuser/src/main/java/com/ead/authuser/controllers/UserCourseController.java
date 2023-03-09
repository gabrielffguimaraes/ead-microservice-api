package com.ead.authuser.controllers;


import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.dtos.UserCourseDto;
import com.ead.authuser.services.UserCourseService;
import com.ead.authuser.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigInteger;

@Log4j2
@RestController
@RequestMapping("api/userCourse")
public class UserCourseController {

    @Autowired
    CourseClient userClient;

    private final UserService userService;
    private final UserCourseService userCourseService;

    public UserCourseController(UserService userService, UserCourseService userCourseService) {
        this.userService = userService;
        this.userCourseService = userCourseService;
    }

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<CourseDto>> getAllCoursesById(@PageableDefault(
            direction = Sort.Direction.ASC,
            size = 10,
            page = 0,
            sort = "courseId") Pageable pageable, @PathVariable(value = "userId") BigInteger userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userClient.getAllCoursesByUser(userId,pageable));
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{userId}/courses/subscription")
    public ResponseEntity<?> userSubscriptionInCourse(@PathVariable("userId") BigInteger userId, @RequestBody @Valid UserCourseDto userCourseDto) {
        var user = this.userService.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found in database."));
        log.info("Inserindo course subscription");
        log.info("User UID [{}]", userId);
        log.info("Course UID [{}]", userCourseDto);
        if (!userCourseService.existsByUserModelAAndCourseId(user, userCourseDto.getCourseId())) {
            this.userCourseService.subscription(userId, userCourseDto.getCourseId());
            return ResponseEntity.status(HttpStatus.CREATED).body("User subscribed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already subscribed in this course .");
        }
    }

    @PostMapping("/course/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void undoSubscriptionsInCourse(@PathVariable("courseId") Integer couseId) {
        // TODO
    }
}
