package com.ead.authuser.repository;

import com.ead.authuser.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    Page<User> findAll(Specification specification,Pageable pageable);
    Page<User> findAll(Pageable pageable);
}
