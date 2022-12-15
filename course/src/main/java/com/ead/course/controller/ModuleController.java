package com.ead.course.controller;

import com.ead.course.dto.ModuleDto;
import com.ead.course.models.Module;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.specification.ModuleSpecification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/module")
public class ModuleController {

    private final ModuleRepository moduleRepository;

    public ModuleController(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @GetMapping
    public ResponseEntity<List<ModuleDto>> listAll(@RequestParam(value="courseId" , required = false) UUID courseId, @RequestParam(value = "title", required = false) String title) {
        return ResponseEntity.status(HttpStatus.OK).body(this.moduleRepository.findAll(ModuleSpecification.filter(title , courseId))
                .stream()
                .map(ModuleDto::of)
                .collect(Collectors.toList()));
    }
}
