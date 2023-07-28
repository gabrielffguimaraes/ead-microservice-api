package com.ead.authuser.controllers;

import com.ead.authuser.configs.RestTemplateApi;
import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enums.Course;
import com.ead.authuser.filters.UserFilter;
import com.ead.authuser.models.User;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@Slf4j
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RestTemplateApi restTemplate;

    @PreAuthorize("hasRole('ADMIN') and hasRole('INSTRUCTOR') and hasRole('STUDENT')")
    @GetMapping
    @JsonView(UserDto.UserView.ResponsePost.class)
    public ResponseEntity<Page<UserDto>> getAllUsers(UserFilter userFilter,
                                                     @PageableDefault(page=0 ,size=20 , sort = "userId" , direction = Sort.Direction.DESC)
                                                     @RequestParam(required = false) UUID courseId,
                                                     Pageable pageable,
                                                     Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("User that is requesting : {}", userDetails.getUsername());
        log.info("Listing all users");
        
        var list = userService.findAll(userFilter,courseId,pageable);
        List<UserDto> listDto = Arrays.asList(modelMapper.map(list.getContent(),UserDto[].class));
        return ResponseEntity.ok(new PageImpl<>(listDto,pageable,list.getTotalElements()));
    }

    @GetMapping( "studentsNotIn/course/{courseId}")
    @JsonView(UserDto.UserView.ResponsePost.class)
    public ResponseEntity<?> getAllUsers(@PathVariable UUID courseId) {
        var list = userService.findAll(courseId);
        log.info("List of students : {}" , list);
        List<UserDto> listDto = Arrays.asList(modelMapper.map(list,UserDto[].class));
        return ResponseEntity.ok(listDto);
    }

    @CircuitBreaker(name = "circuitbreakerInstance")
    @GetMapping("/{userId}/courses")
    public ResponseEntity<?> getAllCoursesByUser(@PathVariable UUID userId) {
        List<Course> courses = restTemplate.getAllCoursesByUser(userId);
        return ResponseEntity.ok(courses);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") UUID userId) {
        Optional<User> userModelOptional = userService.findById(userId);
        if(userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID userId) {
        Optional<User> user = userService.findById(userId);
        if(user.isPresent()) {
            userService.deleteUser(user.get());
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"User deleted succesful\" }");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
