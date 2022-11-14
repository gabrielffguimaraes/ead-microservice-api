package com.ead.course.repository;

import com.ead.course.models.CourseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseScheduleRepository extends JpaRepository<CourseSchedule, UUID> { }
