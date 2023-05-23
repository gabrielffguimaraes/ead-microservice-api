package com.ead.course.validation;


import com.ead.course.dto.CourseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class CourseValidator implements Validator {



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
        /*
        try {

            BigInteger instructor = BigInteger.valueOf(Long.parseLong(course.getUserInstructor().trim()));
            log.info("Instructor [{}]",instructor);
            ResponseEntity<UserDto> result = this.authuserClient.getOneUserById(instructor);
            UserDto userDto = result.getBody();
            if(!userDto.getUserType().equals(UserType.INSTRUCTOR)) {
                errors.rejectValue("userInstructor","400","Erro encontrado deve ser do tipo instrutor .");
            }
        } catch (HttpStatusCodeException e) {
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
                errors.rejectValue("userInstructor" , "400","Instrutor não encontrado");
            }
        } catch (IllegalArgumentException e) {
            errors.rejectValue("userInstructor" , "400","Instrutor Inválido .");
        }*/
    }
}
