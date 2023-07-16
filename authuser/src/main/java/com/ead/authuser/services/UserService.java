package com.ead.authuser.services;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.filters.UserFilter;
import com.ead.authuser.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<User> findAll();

    List<User> findAll(UUID courseId);

    Optional<User> findById(UUID userId);
    void deleteById(UUID userId);

    User save(User userDto);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User update(UUID userId, UserDto userDto);

    Page<User> findAll(UserFilter userFilter, UUID courseId, Pageable pageable);


    public User saveUser(UserDto user);

    public void deleteUser(User userModel);
    public User updateUser(UserDto user);

    public User updatePassword(User user);
}
