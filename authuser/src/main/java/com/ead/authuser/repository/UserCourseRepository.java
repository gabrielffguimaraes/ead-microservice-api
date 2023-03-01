package com.ead.authuser.repository;

import com.ead.authuser.models.User;
import com.ead.authuser.models.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;


public interface UserCourseRepository extends JpaRepository<UserCourse, BigInteger> {
    boolean existsByUserModelAndCourseId(User user, BigInteger courseId);
}
