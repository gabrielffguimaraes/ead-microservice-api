package com.ead.course.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "tb_courses_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseUser implements Serializable {
    private static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "courseId",referencedColumnName = "courseId")
    Course course;

    @Column(nullable = false)
    BigInteger userId;
}
