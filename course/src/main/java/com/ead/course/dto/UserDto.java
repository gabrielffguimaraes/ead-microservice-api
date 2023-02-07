package com.ead.course.dto;

import com.ead.course.enums.UserType;
import lombok.Data;


import java.util.UUID;

@Data
public class UserDto {
    private UUID userId;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String cpf;
    private String cep;
    private UserType userType;
}

