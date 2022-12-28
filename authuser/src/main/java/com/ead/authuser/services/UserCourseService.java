package com.ead.authuser.services;

import com.ead.authuser.models.User;

import java.util.UUID;

public interface UserCourseService {
    boolean existsByUserModelAAndCourseId(User user, UUID courseId);
}
