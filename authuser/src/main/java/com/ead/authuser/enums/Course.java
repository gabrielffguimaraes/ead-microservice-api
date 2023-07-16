package com.ead.authuser.enums;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;


    private UUID courseId;
    private String name;
    private String description;
    private String imageUrl;


    private LocalDateTime creationDate;
    private LocalDateTime lastUpdateDate;

    private CourseStatus courseStatus;

    private CourseLevel courseLevel;

    private BigInteger userInstructor;


}
