package com.ead.course.mock;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.ead.course.models.*;
import com.ead.course.models.Module;
import com.ead.course.repository.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;


public class app implements ApplicationRunner {
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final ModuleRepository moduleRepository;

    private final ScheduleRepository scheduleRepository;

    private final CourseScheduleRepository courseScheduleRepository;

    private final CourseUserRepository courseUserRepository;

    public app(CourseRepository courseRepository, LessonRepository lessonRepository, ModuleRepository moduleRepository, ScheduleRepository scheduleRepository, CourseScheduleRepository courseScheduleRepository, CourseUserRepository courseUserRepository) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.moduleRepository = moduleRepository;
        this.scheduleRepository = scheduleRepository;
        this.courseScheduleRepository = courseScheduleRepository;
        this.courseUserRepository = courseUserRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        var segunda = this.scheduleRepository.save(Schedule.builder().day("Segunda").build());
        var terca = this.scheduleRepository.save(Schedule.builder().day("Terça").build());
        var quarta = this.scheduleRepository.save(Schedule.builder().day("Quarta").build());
        var quinta = this.scheduleRepository.save(Schedule.builder().day("Quinta").build());
        var sexta =  this.scheduleRepository.save(Schedule.builder().day("Sexta").build());
        var sabado =  this.scheduleRepository.save(Schedule.builder().day("Sabado").build());
        var domingo = this.scheduleRepository.save(Schedule.builder().day("Domingo").build());

        var course1 = this.courseRepository.save(Course.builder().name("Curso de algoritmos").courseStatus(CourseStatus.CONCLUDED).courseLevel(CourseLevel.INTERMEDIARY).description("Curso base 1").build());
        var course2 = this.courseRepository.save(Course.builder().name("Curso de pitagoras").courseStatus(CourseStatus.CONCLUDED).courseLevel(CourseLevel.ADVANCED).description("Curso base 2").build());
        var course3 = this.courseRepository.save(Course.builder().name("teste").courseStatus(CourseStatus.INPROGRESS).courseLevel(CourseLevel.BEGINNER).description("teste").build());

        var module1 = Module.builder().title("Modulo 1").description("Modulo de exatas").build();
        var module2 = Module.builder().title("Modulo 2").description("Modulo de humanas").build();

        module1.setCourse(course1);
        module2.setCourse(course2);


        this.moduleRepository.save(module1);
        this.moduleRepository.save(module2);

        var lesson1 = Lesson.builder().title("Aula 1").description("Aula 1 primavera").module(module1).build();
        var lesson2 = Lesson.builder().title("Aula 2").description("Aula 2 verão").module(module1).build();

        this.lessonRepository.save(lesson1);
        this.lessonRepository.save(lesson2);


        /*CURSO 1*/
        this.courseScheduleRepository.save(CourseSchedule.builder().course(course1).schedule(segunda).build());
        this.courseScheduleRepository.save(CourseSchedule.builder().course(course1).schedule(quarta).build());
        this.courseScheduleRepository.save(CourseSchedule.builder().course(course1).schedule(sexta).build());

        /*CURSO 2*/
        this.courseScheduleRepository.save(CourseSchedule.builder().course(course2).schedule(terca).build());
        this.courseScheduleRepository.save(CourseSchedule.builder().course(course2).schedule(quinta).build());

        var courseUser1 = CourseUser
                .builder()
                .userId(UUID.fromString("9eba3b56-9f59-4947-a7ea-dcd3ea54049c"))
                .course(course1)
                .build();
        var courseUser2 = CourseUser
                .builder()
                .userId(UUID.fromString("9eba3b56-9f59-4947-a7ea-dcd3ea54049c"))
                .course(course2)
                .build();
        var courseUser3 = CourseUser
                .builder()
                .userId(UUID.fromString("9eba3b56-9f59-4947-a7ea-dcd3ea54049c"))
                .course(course3)
                .build();
        var courseUser4 = CourseUser
                .builder()
                .userId(UUID.fromString("5223771b-5d17-4134-b1e8-539df131f642"))
                .course(course1)
                .build();
        var courseUser5 = CourseUser
                .builder()
                .userId(UUID.fromString("5223771b-5d17-4134-b1e8-539df131f642"))
                .course(course2)
                .build();
        this.courseUserRepository.save(courseUser1);
        this.courseUserRepository.save(courseUser2);
        this.courseUserRepository.save(courseUser3);
        this.courseUserRepository.save(courseUser4);
        this.courseUserRepository.save(courseUser5);
    }
}
