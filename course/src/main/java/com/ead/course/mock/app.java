package com.ead.course.mock;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.ead.course.models.Module;
import com.ead.course.models.*;
import com.ead.course.repository.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;


public class app implements ApplicationRunner {
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final ModuleRepository moduleRepository;

    private final ScheduleRepository scheduleRepository;

    private final CourseScheduleRepository courseScheduleRepository;


    public app(CourseRepository courseRepository, LessonRepository lessonRepository, ModuleRepository moduleRepository, ScheduleRepository scheduleRepository, CourseScheduleRepository courseScheduleRepository) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.moduleRepository = moduleRepository;
        this.scheduleRepository = scheduleRepository;
        this.courseScheduleRepository = courseScheduleRepository;
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
    }
}
