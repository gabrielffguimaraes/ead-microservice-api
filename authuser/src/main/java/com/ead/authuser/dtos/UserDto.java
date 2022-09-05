package com.ead.authuser.dtos;

import com.ead.authuser.validation.UsernameConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotBlank;

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
    @JsonView({UserView.ResponsePost.class,UserView.RegistrationPost.class})
    private String username;
    @NotBlank(groups = UserView.RegistrationPost.class)
    @JsonView({UserView.ResponsePost.class,UserView.RegistrationPost.class})
    private String email;
    @NotBlank(groups = {UserView.RegistrationPost.class,UserView.PasswordPut.class})
    @JsonView({UserView.RegistrationPost.class,UserView.PasswordPut.class})
    private String password;
    @NotBlank(groups = UserView.PasswordPut.class)
    @JsonView({UserView.PasswordPut.class})
    private String oldPassword;

    @JsonView({UserView.ResponsePost.class,UserView.RegistrationPost.class,UserView.UserPut.class})
    private String fullName;
    @JsonView({UserView.ResponsePost.class,UserView.RegistrationPost.class,UserView.UserPut.class})
    private String phoneNumber;
    @JsonView({UserView.ResponsePost.class,UserView.RegistrationPost.class,UserView.UserPut.class})
    private String cpf;

    @NotBlank(groups = UserView.ImagePut.class)
    @JsonView({UserView.ImagePut.class})
    private String imageUrl;
}
