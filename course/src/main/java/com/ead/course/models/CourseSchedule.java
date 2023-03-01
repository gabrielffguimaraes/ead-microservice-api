package com.ead.course.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tb_course_schedule")
public class CourseSchedule implements Serializable {
    private final static long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger courseScheduleId;

    @ManyToOne
    @JoinColumn(name = "courseId",referencedColumnName = "courseId")
    Course course;

    @ManyToOne
    @JoinColumn(name = "scheduleId",referencedColumnName = "scheduleId")
    Schedule schedule;
}
