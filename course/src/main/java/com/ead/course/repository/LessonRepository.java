package com.ead.course.repository;

import com.ead.course.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface LessonRepository extends JpaRepository<Lesson, BigInteger> {

}
