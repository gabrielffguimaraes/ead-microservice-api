package com.ead.course.services;

import com.ead.course.models.Course;
import com.ead.course.repository.CourseRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.math.BigInteger;
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

    public boolean existByCourseAndUser(UUID courseId, UUID userId) {
        return this.courseRepository.existsByCourseAndUser(courseId,userId);
    }

    @Transactional
    public void saveSubscriptionUserInCourse(UUID courseId, UUID userId) {
        this.courseRepository.saveSubscriptionInCourse(courseId,userId);
    }

    public List<Course> findAll(Specification<Course> courseSpecification) {
        return this.courseRepository.findAll(courseSpecification);
    }
}
