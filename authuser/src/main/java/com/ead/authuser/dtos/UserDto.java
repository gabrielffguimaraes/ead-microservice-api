package com.ead.authuser.dtos;

import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    public interface UserView {
        public static interface RegistrationPost {}
        public static interface UserPut {}
        public static interface PasswordPut {}
        public static interface ImagePut {}
    }
    private UUID userId;
    @JsonView(UserView.RegistrationPost.class)
    private String username;
    @JsonView(UserView.RegistrationPost.class)
    private String email;
    @JsonView(UserView.RegistrationPost.class)
    private String password;
    private String fullName;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;
}
