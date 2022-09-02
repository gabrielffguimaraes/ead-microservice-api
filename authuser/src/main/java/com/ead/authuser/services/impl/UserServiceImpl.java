package com.ead.authuser.services.impl;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repository.UserRepository;
import com.ead.authuser.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.time.LocalDateTime.now;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;


    /**
     * @return List<UserModel>
     * method should return a list of registered users
     */
    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    /**
     * @param userId
     * @return Optional<UserModel>
     * method must return a registered user filtered by id
     */
    @Override
    public Optional<UserModel> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    /**
     * @param userId
     * method to delete an user by id
     */
    @Override
    public void deleteById(UUID userId) {
        userRepository.deleteById(userId);
    }

    /**
     * @param userDto
     * @return UserModel
     * method must save a new user
     */
    @Override
    public UserModel save(UserDto userDto) {
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setUserType(UserType.STUDENT);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setCreationDate(now(ZoneId.of("America/Sao_Paulo")));
        userModel.setLastUpdateDate(now(ZoneId.of("America/Sao_Paulo")));
        return userRepository.save(userModel);
    }

    /**
     * @param username
     * @return boolean
     * must check if username is already taken , case yes then block request of finishing .
     */
    @Override
    public boolean existsByUsername(String username) {
        var userModel = userRepository.findByUsername(username);
        return userModel.isPresent();
    }

    /**
     * @param email
     * @return boolean
     * must check if email is already taken , case yes then block request of finishing
     */
    @Override
    public boolean existsByEmail(String email) {
        var userModel = userRepository.findByEmail(email);
        return userModel.isPresent();
    }
}
