package com.ead.course.controller;

import com.ead.course.clients.CourseClient;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.awt.print.Pageable;
import java.util.UUID;

@RestController
@RequestMapping("api/courseUser")
public class CourseUserController {

    @Autowired
    public CourseClient courseClient;

    @GetMapping("courses/{courseId}/users")
    public ResponseEntity<?> getAllUsersByCourse(@PathVariable("courseId") UUID courseId) {
        return courseClient.getAllUsersByCourse(courseId);
    }
}
