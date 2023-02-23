package com.ead.course.dto;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class CourseDto {
    private UUID courseId;
    @NotBlank(message = "* curso obrigatório")
    private String name;
    @NotNull(message = "descrição não pode ser vazia.")
    private String description;
    private String imageUrl;
    private CourseStatus courseStatus;
    private CourseLevel courseLevel;

    @NotNull(message = "Instrutor obrigatório")
    private String userInstructor;
}
