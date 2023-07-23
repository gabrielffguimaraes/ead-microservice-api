package com.ead.authuser.repository;

import com.ead.authuser.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.FetchType;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    @EntityGraph(attributePaths = "roles", type = EntityGraph.EntityGraphType.FETCH)
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    Page<User> findAll(Specification specification,Pageable pageable);
    Page<User> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM tb_users t1 LEFT JOIN tb_users_courses t2 ON t1.user_id = t2.user_id " +
            "WHERE (t2.course_id != :courseId or t2.course_id is null) and t1.user_type='STUDENT' " , nativeQuery = true)
    List<User> findStudentsNotInCourse(UUID courseId);
}
