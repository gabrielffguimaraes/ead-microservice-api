package com.ead.course.models;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;
import java.math.BigInteger;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_courses")
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger courseId;
    private String name;
    private String description;
    private String imageUrl;
    //@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy HH:mm:ss")

    private LocalDateTime creationDate;
    //@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime lastUpdateDate;
    @Column
    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;
    @Column
    @Enumerated(EnumType.STRING)
    private CourseLevel courseLevel;

    private BigInteger userInstructor;

    @JsonIgnoreProperties("course")
    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Module> modules;

    @ManyToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "tb_course_schedule",
            joinColumns = @JoinColumn(name = "courseId"),
            inverseJoinColumns = @JoinColumn(name = "scheduleId")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Schedule> schedule;

    @PrePersist
    public void prePersist() {
        this.setCreationDate(LocalDateTime.now());
        this.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
    }

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Set<CourseUser> courseUsers;

    public CourseUser convertToCourseUser(BigInteger userId) {
        return CourseUser.builder()
                .userId(userId)
                .course(this)
                .build();
    }
}
