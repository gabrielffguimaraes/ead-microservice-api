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
import java.util.UUID;

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
            sort = "courseId") Pageable pageable, @PathVariable(value = "userId") UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userClient.getAllCoursesByUser(userId,pageable));
    }


    @PostMapping("/users/{userId}/courses/subscription")
    public ResponseEntity<?> userSubscriptionInCourse(@PathVariable("userId") UUID userId,@RequestBody @Valid UserCourseDto userCourseDto) {
        var user = this.userService.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found in database."));
        if(userCourseService.existsByUserModelAAndCourseId(user,userCourseDto.getCourseId())) {

        }
        return null;
    }
}
