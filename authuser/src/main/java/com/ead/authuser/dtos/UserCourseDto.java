package com.ead.authuser.dtos;

import com.ead.authuser.models.User;
import lombok.Data;

import java.math.BigInteger;
import java.util.UUID;


@Data
public class UserCourseDto {
    private UUID id;
    private UUID courseId;
    private User userModel;
}
