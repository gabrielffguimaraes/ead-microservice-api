package com.ead.authuser.services;
import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<UserModel> findAll();
    Optional<UserModel> findById(UUID userId);
    void deleteById(UUID userId);

    UserModel save(UserDto userDto);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    UserModel update(UUID userId, UserDto userDto);

    Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable);
}
