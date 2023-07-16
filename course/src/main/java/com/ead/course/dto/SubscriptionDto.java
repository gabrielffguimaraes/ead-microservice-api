package com.ead.course.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.UUID;

@Data
public class SubscriptionDto {
    @NotNull
    private UUID userId;
}
