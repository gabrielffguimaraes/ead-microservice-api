package com.ead.authuser.services.impl;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.dtos.UserEventDto;
import com.ead.authuser.enums.ActionType;
import com.ead.authuser.filters.UserFilter;
import com.ead.authuser.models.User;
import com.ead.authuser.publishers.UserEventPublisher;
import com.ead.authuser.repository.UserRepository;
import com.ead.authuser.services.UserService;
import com.ead.authuser.specification.UserSpec;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserEventPublisher userEventPublisher;

    /**
     * @return List<User>
     * method should return a list of registered users
     */
    @Override
    public List<User> findAll() {

        return userRepository.findAll();
    }

    /**
     * @param userId
     * @return Optional<User>
     * method must return a registered user filtered by id
     */
    @Override
    public Optional<User> findById(BigInteger userId) {
        return userRepository.findById(userId);
    }

    /**
     * @param userId
     * method to delete an user by id
     */
    @Override
    public void deleteById(BigInteger userId) {
        userRepository.deleteById(userId);
    }

    /**
     * @param userDto
     * @return User
     * method must save a new user
     */
    @Override
    public User save(User user) {
        return userRepository.save(user);
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

    /**
     * @param userId
     * @param userDto
     * @return User
     * must update a user partially in database
     */
    @Override
    public User update(BigInteger userId, UserDto userDto) {
        var user = findById(userId).get();
        user.setFullName(userDto.getFullName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setCpf(userDto.getCpf());
        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        return userRepository.save(user);
    }

    @Override
    public Page<User> findAll(UserFilter userFilter, BigInteger courseId, Pageable pageable) {
        return null;
    }

    @Override
    public List<User> findAll(BigInteger courseId) {
        log.info("Course id [{}]", courseId);
        return userRepository.findStudentsNotInCourse(courseId);
    }

    @Transactional
    @Override
    public User saveUser(UserDto userDto) {
        var user = new User();
        BeanUtils.copyProperties(userDto, user);
        User userSaved = this.save(user);
        UserEventDto userEventDto = new ModelMapper().map(userSaved,UserEventDto.class);
        userEventPublisher.publishUserEvent(userEventDto, ActionType.CREATE);
        return userSaved;
    }
}
