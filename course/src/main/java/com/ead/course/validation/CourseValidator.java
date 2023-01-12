package com.ead.course.validation;


import com.ead.course.clients.AuthuserClient;
import com.ead.course.dto.CourseDto;
import com.ead.course.dto.UserDto;
import com.ead.course.enums.UserType;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.Validation;
import java.util.UUID;

@Component
@Slf4j
public class CourseValidator implements Validator {
    @Autowired
    @Qualifier("mvcValidator")
    private Validator validator;

    @Autowired
    AuthuserClient authuserClient;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object curso, Errors errors) {
        log.info("TESTE {}",curso);
        //CourseDto courseDto = (CourseDto) curso;
        validator.validate(curso,errors);
       /* if(!errors.hasErrors()) {
            validateUserInstructor(courseDto.getUserInstructor(),errors);
        }*/
    }

    private void validateUserInstructor(UUID userInstructor, Errors errors) {
        ResponseEntity<UserDto> responseUserInstructor;
        try {
            responseUserInstructor = authuserClient.getOneUserById(userInstructor);
            if(responseUserInstructor.getBody().getUserType().equals(UserType.STUDENT)) {
                errors.rejectValue("userInstructor","UserInstructorError","User must be INSTRUCTOR or ADMIN");
            };
        } catch (HttpStatusCodeException e) {
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                errors.rejectValue("userInstructor","UserInstructorNotFound","Instructor not found.");
            }
        }
    }
}
