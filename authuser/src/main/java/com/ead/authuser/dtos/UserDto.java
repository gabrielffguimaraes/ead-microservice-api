package com.ead.authuser.dtos;

import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.User;
import com.ead.authuser.validation.CepConstraint;
import com.ead.authuser.validation.UsernameConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.*;
import java.math.BigInteger;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    public interface UserView {
        public static interface ResponsePost {}
        public static interface RegistrationPost {}
        public static interface UserPut {}
        public static interface PasswordPut {}
        public static interface ImagePut {}
    }
    @JsonView(UserView.ResponsePost.class)
    private UUID userId;

    @UsernameConstraint(groups = UserView.RegistrationPost.class)
    @NotBlank(groups = UserView.RegistrationPost.class)
    @JsonView({UserView.ResponsePost.class,UserView.RegistrationPost.class,UserView.UserPut.class})
    private String username;
    @NotBlank(groups = UserView.RegistrationPost.class)
    @Email(groups = UserView.RegistrationPost.class,message = "Email inválido .")
    @JsonView({UserView.ResponsePost.class,UserView.RegistrationPost.class,UserView.UserPut.class})
    private String email;
    @NotBlank(groups = {UserView.RegistrationPost.class,UserView.PasswordPut.class} , message = "Password obrigatório")
    @JsonView({UserView.RegistrationPost.class,UserView.PasswordPut.class})
    private String password;
    @NotBlank(groups = UserView.PasswordPut.class)
    @JsonView({UserView.PasswordPut.class})
    private String oldPassword;

    @JsonView({UserView.ResponsePost.class,UserView.RegistrationPost.class,UserView.UserPut.class})
    private String fullName;
    @JsonView({UserView.ResponsePost.class,UserView.RegistrationPost.class,UserView.UserPut.class})
    private String phoneNumber;

    @CPF(groups = UserView.RegistrationPost.class)
    @NotNull(groups = UserView.RegistrationPost.class,message = "CPF obrigatório")
    @JsonView({UserView.ResponsePost.class,UserView.RegistrationPost.class,UserView.UserPut.class})
    private String cpf;

    @NotBlank(groups = UserView.ImagePut.class)
    @JsonView({UserView.ImagePut.class})
    private String imageUrl;

    @CepConstraint(groups = UserView.RegistrationPost.class,message = "Cep Inválido")
    @Digits(fraction = 0,message = "Obrigatoriedade de 8 digitos para o cep",integer = 8)
    @JsonView({UserView.RegistrationPost.class,UserView.ResponsePost.class})
    private String cep;

    @JsonView({UserView.RegistrationPost.class,UserView.ResponsePost.class})
    private UserType userType;


    public User convertToUser() {
        return new ModelMapper().map(this,User.class);
    }
}
