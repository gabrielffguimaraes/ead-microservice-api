package com.ead.course.services;

import com.ead.course.models.UserModel;
import com.ead.course.repository.UserRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserModel> findAll(Specification<UserModel> spec) {
        return this.userRepository.findAll(spec);
    }
}
