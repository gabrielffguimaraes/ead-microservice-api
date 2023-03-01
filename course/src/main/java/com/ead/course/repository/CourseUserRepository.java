package com.ead.course.repository;

import com.ead.course.models.Course;
import com.ead.course.models.CourseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
@Repository
public interface CourseUserRepository extends JpaRepository<CourseUser, BigInteger> {
    boolean existsByCourseAndUserId(Course course,BigInteger userId);
}
