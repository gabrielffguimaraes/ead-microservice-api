package com.ead.authuser.dtos;

import com.ead.authuser.models.User;
import lombok.Data;

import java.math.BigInteger;


@Data
public class UserCourseDto {
    private BigInteger id;
    private BigInteger courseId;
    private User userModel;
}
