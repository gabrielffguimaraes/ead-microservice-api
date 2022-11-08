package com.ead.course.mock;

import com.ead.course.models.Course;
import com.ead.course.models.Lesson;
import com.ead.course.models.Module;
import com.ead.course.repository.CourseRepository;
import com.ead.course.repository.LessonRepository;
import com.ead.course.repository.ModuleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class app implements ApplicationRunner {
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final ModuleRepository moduleRepository;

    public app(CourseRepository courseRepository, LessonRepository lessonRepository, ModuleRepository moduleRepository) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.moduleRepository = moduleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        var course1 = this.courseRepository.save(Course.builder().name("Curso de algoritmos").description("Curso base 1").build());
        var course2 = this.courseRepository.save(Course.builder().name("Curso de pitagoras").description("Curso base 2").build());

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
    }
}
