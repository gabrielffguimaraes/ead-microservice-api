package com.ead.course.validation;


import com.ead.course.dto.CourseDto;
import com.ead.course.enums.UserType;
import com.ead.course.models.UserModel;
import com.ead.course.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Slf4j
@Component
public class CourseValidator implements Validator {

    @Autowired
    private UserService userService;
    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        validator.validate(target,errors);
        CourseDto course = (CourseDto) target;
        if(!errors.hasErrors()) {
            this.customValidation(course,errors);
        }
    }

    public void customValidation(CourseDto course, Errors errors) {
        Optional<UserModel> userModelOptional = userService.findById(course.getUserInstructor());
        if(userModelOptional.isEmpty()) {
            errors.rejectValue("userInstructor","UserInstructorError","Instructor not found !");
            return;
        }
        if(userModelOptional.get().getUserType().equals(UserType.STUDENT.toString())) {
            errors.rejectValue("userInstructor","UserInstructorError","User must be INSTRUCTOR or ADMIN");
        }
    }
}
