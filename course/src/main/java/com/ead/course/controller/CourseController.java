package com.ead.course.controller;

import com.ead.course.dto.CourseDto;
import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.ead.course.models.Course;
import com.ead.course.repository.CourseRepository;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.specification.CourseSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Tag(name = "Cursos",description = "Endpoint responsável por manter cursos")
@RestController
@RequestMapping("api/course")
public class CourseController {
    private final CourseRepository courseRepository;
    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Operation(summary = "Listar cursos .")
    @GetMapping
    public ResponseEntity<Page<CourseDto>> findAll(@PageableDefault(page=0,size=10,sort = {"courseId"},direction = Sort.Direction.DESC) Pageable page,
                                                   @RequestParam(value="name", required=false) String name,
                                                   @RequestParam(value="courseLevel" , required=false)CourseLevel courseLevel,
                                                   @RequestParam(value="courseStatus" , required=false)CourseStatus courseStatus,
                                                   @RequestParam(required = false) UUID userId
                                     ) {
        var result = this.courseRepository.findAll(CourseSpecification.filter(courseLevel,courseStatus,name,userId),page);
        List courses = result.stream().map(course -> new ModelMapper().map(course, CourseDto.class)).collect(Collectors.toList());
        var response = new PageImpl<CourseDto>(courses,page,result.getTotalElements());
        return ResponseEntity.ok(response);
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
