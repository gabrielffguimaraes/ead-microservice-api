package com.ead.course.repository;

import com.ead.course.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

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
}
