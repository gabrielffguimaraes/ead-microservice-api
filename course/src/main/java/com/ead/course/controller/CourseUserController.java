package com.ead.course.controller;

import com.ead.course.dto.ContentResponseDto;
import com.ead.course.dto.SubscriptionDto;
import com.ead.course.enums.UserStatus;
import com.ead.course.enums.UserType;
import com.ead.course.models.Course;
import com.ead.course.models.UserModel;
import com.ead.course.services.CourseService;
import com.ead.course.services.UserService;
import com.ead.course.specification.CourseSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
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

    @GetMapping("users/{userId}/courses")
    public ResponseEntity<?> getAllCoursesByUserId(@PathVariable("userId") UUID userId) {
        List<Course> courses = courseService.findAll(CourseSpecification.filterCoursesByUser(userId));
        log.info("LISTANDO CURSOS == {}",courses);
        ContentResponseDto<Course> contentResponseDto = ContentResponseDto.<Course>builder().content(courses).build();
        return ResponseEntity.ok(contentResponseDto);
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<?> saveSubscriptionInCourse(@PathVariable UUID courseId,
                                                      @RequestBody @Valid SubscriptionDto subscriptionDto) {
        Optional<Course> courseOptional = courseService.findCourseById(courseId);
        if (!courseOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Course not found.");
        }

        if (courseService.existByCourseAndUser(courseId,subscriptionDto.getUserId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Subscription already exist");
        }

        Optional<UserModel> userModel = userService.findById(subscriptionDto.getUserId().toString());
        if(!userModel.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found !");
        }
        if(userModel.get().getUserStatus().equals(UserStatus.BLOCKED.toString())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User is blocked");
        }
        this.courseService.saveSubscriptionUserInCourse(courseId,subscriptionDto.getUserId());
        this.courseService.notificate(courseOptional.get(),userModel.get());
        return ResponseEntity.status(HttpStatus.CREATED).body("Subscription created successfully .");
    }


}
