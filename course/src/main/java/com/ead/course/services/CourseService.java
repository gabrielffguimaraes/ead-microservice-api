package com.ead.course.services;

import com.ead.course.dto.NotificationCommandDto;
import com.ead.course.models.Course;
import com.ead.course.models.UserModel;
import com.ead.course.publisher.NotificationCommandPublisher;
import com.ead.course.repository.CourseRepository;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.math.BigInteger;
import java.util.UUID;

@Slf4j
@Service
public class CourseService {

    @Autowired
    private NotificationCommandPublisher notificationCommandPublisher;

    private final CourseRepository courseRepository;

    public Optional<Course> findCourseById(UUID courseId) {
        return this.courseRepository.findById(courseId);
    }

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public boolean existByCourseAndUser(UUID courseId, UUID userId) {
        return this.courseRepository.existsByCourseAndUser(courseId,userId);
    }

    @Transactional
    public void saveSubscriptionUserInCourse(UUID courseId, UUID userId) {
        this.courseRepository.saveSubscriptionInCourse(courseId,userId);
    }
    public void notificate(Course course, UserModel userModel) {
        try {
            var notificationCommandDto = new NotificationCommandDto();
            notificationCommandDto.setTitle("Bem-Vindo(a) ao Curso " + course.getName());
            notificationCommandDto.setMessage(userModel.getFullName() + " a sua inscrição foi realizada com sucesso !");
            notificationCommandDto.setUserId(userModel.getUserId());
            notificationCommandPublisher.publishNotificationCommand(notificationCommandDto);
        } catch (Exception e) {
            log.info("Error sending notification!");
        }
    }
    public List<Course> findAll(Specification<Course> courseSpecification) {
        return this.courseRepository.findAll(courseSpecification);
    }


}
