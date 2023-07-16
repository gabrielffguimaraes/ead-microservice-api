package com.ead.course.dto;

import com.ead.course.models.UserModel;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.math.BigInteger;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEventDto {
    private UUID userId;
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;
    private String cep;
    private String userType;
    private String actionType;

    public UserModel convertToUserModel(){
        var userModel = new UserModel();
        BeanUtils.copyProperties(this,userModel);
        userModel.setUserId(this.userId);
        return userModel;
    }
}
