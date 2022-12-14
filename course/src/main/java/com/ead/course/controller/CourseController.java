package com.ead.course.controller;

import com.ead.course.models.Course;
import com.ead.course.repository.CourseRepository;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.specification.CourseSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<?> findAll(@PageableDefault(page=0,size=10) Pageable page,@RequestParam(value="title", required=false) String title) {
        System.out.println(title);
        return ResponseEntity.ok(this.courseRepository.findAll(CourseSpecification.filter(title),page));
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
        course.setCreationDate(LocalDateTime.now());
        course.setLastUpdateDate(LocalDateTime.now());
        return this.courseRepository.save(course);
    }

    @Operation(summary = "Deve atualizar um curso")
    @PutMapping("{id}")
    public Course update(@RequestBody @Valid Course course,@PathVariable("id") String id) {
        var course1 = this.courseRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Curso não encontrado"));
        course1.setName(course.getName());
        course1.setLastUpdateDate(LocalDateTime.now());
        return this.courseRepository.save(course1);
    }

    @Operation(summary="Find by id")
    @GetMapping("{courseId}")
    public Course findById(@PathVariable UUID courseId) {
        return this.courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Curso não encontrado"));
    }
}
