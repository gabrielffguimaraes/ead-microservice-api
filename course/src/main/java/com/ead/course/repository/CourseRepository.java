package com.ead.course.repository;

import com.ead.course.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> , JpaSpecificationExecutor<Course> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {
            "modules",
            "modules.lessons",
            "schedule"
    })
    Page<Course> findAll(Specification spec, Pageable page);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {
            "modules",
            "modules.lessons",
            "schedule"
    })
    long count(Specification<Course> filter);

    @Query(value = "select count(*) > 0 from tb_courses_users where course_id = :course and user_id = :user",nativeQuery = true)
    Boolean existsByCourseAndUser(@Param("course") UUID course,@Param("user") UUID user);

    @Modifying
    @Query(value = "insert into tb_courses_users values (:courseId,:userId)",nativeQuery = true)
    void saveSubscriptionInCourse(@Param("courseId") UUID courseId,@Param("userId") UUID userId);

    @Modifying
    @Query(value = "delete from tb_courses_users where user_id=:userId",nativeQuery = true)
    void deleteCourseUserByUserId(@Param("userId") UUID userId);

    @Modifying
    @Query(value = "delete from tb_courses_users where course_id=:courseId",nativeQuery = true)
    void deleteCourseUserByCourseId(@Param("courseId") UUID courseId);
}
