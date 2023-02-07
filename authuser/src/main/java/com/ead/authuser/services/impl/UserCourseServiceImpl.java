package com.ead.authuser.services.impl;

import com.ead.authuser.models.User;
import com.ead.authuser.models.UserCourse;
import com.ead.authuser.repository.UserCourseRepository;
import com.ead.authuser.services.UserCourseService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserCourseServiceImpl implements UserCourseService {

    private final UserCourseRepository userCourseRepository;


    public UserCourseServiceImpl(UserCourseRepository userCourseRepository) {
        this.userCourseRepository = userCourseRepository;
    }

    @Override
    public boolean existsByUserModelAAndCourseId(User user, UUID courseId) {
        return userCourseRepository.existsByUserModelAndCourseId(user,courseId);
    }

    @Override
    public void subscription(UUID userId, UUID courseId) {
        this.userCourseRepository.save(UserCourse.builder()
                .userModel(User.builder().userId(userId).build())
                .courseId(courseId)
                .build());
    }
}
