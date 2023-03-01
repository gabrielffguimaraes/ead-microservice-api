package com.ead.authuser.services;

import com.ead.authuser.models.User;

import java.math.BigInteger;

public interface UserCourseService {
    boolean existsByUserModelAAndCourseId(User user, BigInteger courseId);

    void subscription(BigInteger userId, BigInteger courseId);
}
