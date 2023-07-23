package com.ead.authuser.services.impl;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.dtos.UserEventDto;
import com.ead.authuser.enums.ActionType;
import com.ead.authuser.enums.RoleType;
import com.ead.authuser.filters.UserFilter;
import com.ead.authuser.models.RoleModel;
import com.ead.authuser.models.User;
import com.ead.authuser.publishers.UserEventPublisher;
import com.ead.authuser.repository.UserRepository;
import com.ead.authuser.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserEventPublisher userEventPublisher;

    @Autowired
    public RoleServiceImpl roleService;

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
    public Optional<User> findById(UUID userId) {
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
     * @param
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
    public User update(UUID userId, UserDto userDto) {
        var user = findById(userId).get();
        user.setFullName(userDto.getFullName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setCpf(userDto.getCpf());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        return userRepository.save(user);
    }

    @Override
    public Page<User> findAll(UserFilter userFilter, UUID courseId, Pageable pageable) {
        if(userFilter.isEmpty()) {
            Example<User> ex = Example.of(User.builder()
                            .cpf(userFilter.getCpf())
                            .email(userFilter.getEmail())
                            .fullName(userFilter.getFullName())
                    .build());
            return userRepository.findAll(ex,pageable);
        }
        return userRepository.findAll(pageable);
    }

    @Override
    public List<User> findAll(UUID courseId) {
        log.info("Course id [{}]", courseId);
        return userRepository.findStudentsNotInCourse(courseId);
    }

    @Transactional
    @Override
    public User saveUser(UserDto userDto) {
        var user = new User();
        BeanUtils.copyProperties(userDto, user);

        RoleModel roleModel = roleService.findByRoleName(RoleType.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Rule not not found !"));
        user.getRoles().add(roleModel);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User userSaved = this.save(user);

        UserEventDto userEventDto = new ModelMapper().map(userSaved,UserEventDto.class);
        userEventDto.setUserId(userSaved.getUserId());
        userEventPublisher.publishUserEvent(userEventDto, ActionType.CREATE);
        return userSaved;
    }

    @Transactional
    @Override
    public void deleteUser(User userModel) {
        deleteById(userModel.getUserId());
        UserEventDto userEventDto = new ModelMapper().map(userModel,UserEventDto.class);
        userEventPublisher.publishUserEvent(userEventDto, ActionType.DELETE);
    }



    @Transactional
    @Override
    public User updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found !"));
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());

        User userUpdated = save(user);
        UserEventDto userEventDto = new ModelMapper().map(userUpdated,UserEventDto.class);
        userEventPublisher.publishUserEvent(userEventDto, ActionType.UPDATE);
        return userUpdated;
    }

    @Transactional
    @Override
    public User updatePassword(User user) {
        return this.save(user);
    }
}
