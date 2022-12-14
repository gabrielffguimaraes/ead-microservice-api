package com.ead.course.controller;

import com.ead.course.models.Course;
import com.ead.course.repository.CourseRepository;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.specification.CourseSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Slf4j
@Tag(name = "Cursos",description = "Endpoint responsável por manter cursos")
@RestController
@RequestMapping("api/course")
public class CourseController {
    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;

    public CourseController(CourseRepository courseRepository, ModuleRepository moduleRepository) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
    }

    @Operation(summary = "Listar cursos .")
    @GetMapping
    public ResponseEntity<?> findAll(@PageableDefault(page=0,size=10) Pageable page) {
        return ResponseEntity.ok(this.courseRepository.findAll(CourseSpecification.filter("teste"),page));
    }

    @Operation(summary = "Deletar cursos")
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") String id) {
        System.out.println(id);
        this.courseRepository.deleteById(UUID.fromString(id));
    }

    @Operation(summary = "Deve salvar um curso")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Course save(@RequestBody @Valid Course course) {
        course.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        course.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return this.courseRepository.save(course);
    }
}
