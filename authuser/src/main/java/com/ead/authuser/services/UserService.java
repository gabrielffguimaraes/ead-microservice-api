package com.ead.authuser.services;
import com.ead.authuser.filters.UserFilter;
import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    List<User> findAll(BigInteger courseId);

    Optional<User> findById(BigInteger userId);
    void deleteById(BigInteger userId);

    User save(User userDto);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User update(BigInteger userId, UserDto userDto);

    Page<User> findAll(UserFilter userFilter, BigInteger courseId, Pageable pageable);


    public User saveUser(UserDto user);
}
