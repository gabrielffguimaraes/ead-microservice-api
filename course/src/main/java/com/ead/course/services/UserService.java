package com.ead.course.services;

import com.ead.course.models.UserModel;
import com.ead.course.repository.CourseRepository;
import com.ead.course.repository.UserRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public UserService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public List<UserModel> findAll(Specification<UserModel> spec) {
        return this.userRepository.findAll(spec);
    }

    public void save(UserModel userModel) {
        this.userRepository.save(userModel);
    }

    @Transactional
    public void delete(UUID userId) {
        this.courseRepository.deleteCourseUserByUserId(userId);
        this.userRepository.deleteById(userId);
    }

    public Optional<UserModel> findById(String userInstructor) {
        return userRepository.findById(UUID.fromString(userInstructor));
    }
}
