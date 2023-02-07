package com.ead.course.validation;


import com.ead.course.clients.AuthuserClient;
import com.ead.course.dto.UserDto;
import com.ead.course.enums.UserType;
import com.ead.course.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.client.HttpStatusCodeException;

@Component
public class CourseValidator implements Validator {

    @Autowired
    AuthuserClient authuserClient;

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
        Course course = (Course) target;
        if(!errors.hasErrors()) {
            this.customValidation(course,errors);
        }
    }

    public void customValidation(Course course, Errors errors) {
        try {
            ResponseEntity<UserDto> result = this.authuserClient.getOneUserById(course.getUserInstructor());
            UserDto userDto = result.getBody();
            if(!userDto.getUserType().equals(UserType.INSTRUCTOR)) {
                errors.rejectValue("userInstructor","400","Erro encontrado deve ser do tipo instrutor .");
            }
        } catch (HttpStatusCodeException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
                errors.rejectValue("userInstructor" , "400","Usuário não encontrado");
            }
        }
    }
}
