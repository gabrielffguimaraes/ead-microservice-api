package com.ead.course.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
public class SubscriptionDto {
    @NotNull
    private BigInteger userId;
}
