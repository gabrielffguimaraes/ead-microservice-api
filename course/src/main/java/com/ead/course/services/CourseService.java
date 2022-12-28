package com.ead.course.services;

import com.ead.course.models.Course;
import com.ead.course.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public Optional<Course> findCourseById(UUID courseId) {
        return this.courseRepository.findById(courseId);
    }

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
}
