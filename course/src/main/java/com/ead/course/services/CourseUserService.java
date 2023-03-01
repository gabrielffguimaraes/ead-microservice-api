package com.ead.course.services;

import com.ead.course.models.Course;
import com.ead.course.models.CourseUser;
import com.ead.course.repository.CourseUserRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class CourseUserService {

    private final CourseUserRepository courseUserRepository;

    public CourseUserService(CourseUserRepository courseUserRepository) {
        this.courseUserRepository = courseUserRepository;
    }

    public boolean existsByCourseAndUserId(Course course, BigInteger userID) {
        return this.courseUserRepository.existsByCourseAndUserId(course,userID);
    }

    public CourseUser save(CourseUser courseUser) {
        return courseUserRepository.save(courseUser);
    }
}
