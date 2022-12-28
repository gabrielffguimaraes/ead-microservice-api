package com.ead.authuser.repository;

import com.ead.authuser.models.User;
import com.ead.authuser.models.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserCourseRepository extends JpaRepository<UserCourse, UUID> {
    boolean existsByUserModelAAndCourseId(User user, UUID courseId);
}
